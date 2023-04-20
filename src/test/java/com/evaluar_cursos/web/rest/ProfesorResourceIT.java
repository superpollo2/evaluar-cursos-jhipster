package com.evaluar_cursos.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.evaluar_cursos.IntegrationTest;
import com.evaluar_cursos.domain.Profesor;
import com.evaluar_cursos.repository.ProfesorRepository;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProfesorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProfesorResourceIT {

    private static final UUID DEFAULT_PROFESOR_ID = UUID.randomUUID();
    private static final UUID UPDATED_PROFESOR_ID = UUID.randomUUID();

    private static final String DEFAULT_PROFESOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROFESOR_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/profesors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfesorMockMvc;

    private Profesor profesor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profesor createEntity(EntityManager em) {
        Profesor profesor = new Profesor().profesorId(DEFAULT_PROFESOR_ID).profesorName(DEFAULT_PROFESOR_NAME);
        return profesor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profesor createUpdatedEntity(EntityManager em) {
        Profesor profesor = new Profesor().profesorId(UPDATED_PROFESOR_ID).profesorName(UPDATED_PROFESOR_NAME);
        return profesor;
    }

    @BeforeEach
    public void initTest() {
        profesor = createEntity(em);
    }

    @Test
    @Transactional
    void createProfesor() throws Exception {
        int databaseSizeBeforeCreate = profesorRepository.findAll().size();
        // Create the Profesor
        restProfesorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profesor)))
            .andExpect(status().isCreated());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeCreate + 1);
        Profesor testProfesor = profesorList.get(profesorList.size() - 1);
        assertThat(testProfesor.getProfesorId()).isEqualTo(DEFAULT_PROFESOR_ID);
        assertThat(testProfesor.getProfesorName()).isEqualTo(DEFAULT_PROFESOR_NAME);
    }

    @Test
    @Transactional
    void createProfesorWithExistingId() throws Exception {
        // Create the Profesor with an existing ID
        profesor.setId(1L);

        int databaseSizeBeforeCreate = profesorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfesorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profesor)))
            .andExpect(status().isBadRequest());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProfesors() throws Exception {
        // Initialize the database
        profesorRepository.saveAndFlush(profesor);

        // Get all the profesorList
        restProfesorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profesor.getId().intValue())))
            .andExpect(jsonPath("$.[*].profesorId").value(hasItem(DEFAULT_PROFESOR_ID.toString())))
            .andExpect(jsonPath("$.[*].profesorName").value(hasItem(DEFAULT_PROFESOR_NAME)));
    }

    @Test
    @Transactional
    void getProfesor() throws Exception {
        // Initialize the database
        profesorRepository.saveAndFlush(profesor);

        // Get the profesor
        restProfesorMockMvc
            .perform(get(ENTITY_API_URL_ID, profesor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(profesor.getId().intValue()))
            .andExpect(jsonPath("$.profesorId").value(DEFAULT_PROFESOR_ID.toString()))
            .andExpect(jsonPath("$.profesorName").value(DEFAULT_PROFESOR_NAME));
    }

    @Test
    @Transactional
    void getNonExistingProfesor() throws Exception {
        // Get the profesor
        restProfesorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProfesor() throws Exception {
        // Initialize the database
        profesorRepository.saveAndFlush(profesor);

        int databaseSizeBeforeUpdate = profesorRepository.findAll().size();

        // Update the profesor
        Profesor updatedProfesor = profesorRepository.findById(profesor.getId()).get();
        // Disconnect from session so that the updates on updatedProfesor are not directly saved in db
        em.detach(updatedProfesor);
        updatedProfesor.profesorId(UPDATED_PROFESOR_ID).profesorName(UPDATED_PROFESOR_NAME);

        restProfesorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProfesor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProfesor))
            )
            .andExpect(status().isOk());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeUpdate);
        Profesor testProfesor = profesorList.get(profesorList.size() - 1);
        assertThat(testProfesor.getProfesorId()).isEqualTo(UPDATED_PROFESOR_ID);
        assertThat(testProfesor.getProfesorName()).isEqualTo(UPDATED_PROFESOR_NAME);
    }

    @Test
    @Transactional
    void putNonExistingProfesor() throws Exception {
        int databaseSizeBeforeUpdate = profesorRepository.findAll().size();
        profesor.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfesorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, profesor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profesor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProfesor() throws Exception {
        int databaseSizeBeforeUpdate = profesorRepository.findAll().size();
        profesor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfesorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profesor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProfesor() throws Exception {
        int databaseSizeBeforeUpdate = profesorRepository.findAll().size();
        profesor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfesorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profesor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProfesorWithPatch() throws Exception {
        // Initialize the database
        profesorRepository.saveAndFlush(profesor);

        int databaseSizeBeforeUpdate = profesorRepository.findAll().size();

        // Update the profesor using partial update
        Profesor partialUpdatedProfesor = new Profesor();
        partialUpdatedProfesor.setId(profesor.getId());

        partialUpdatedProfesor.profesorId(UPDATED_PROFESOR_ID).profesorName(UPDATED_PROFESOR_NAME);

        restProfesorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfesor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProfesor))
            )
            .andExpect(status().isOk());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeUpdate);
        Profesor testProfesor = profesorList.get(profesorList.size() - 1);
        assertThat(testProfesor.getProfesorId()).isEqualTo(UPDATED_PROFESOR_ID);
        assertThat(testProfesor.getProfesorName()).isEqualTo(UPDATED_PROFESOR_NAME);
    }

    @Test
    @Transactional
    void fullUpdateProfesorWithPatch() throws Exception {
        // Initialize the database
        profesorRepository.saveAndFlush(profesor);

        int databaseSizeBeforeUpdate = profesorRepository.findAll().size();

        // Update the profesor using partial update
        Profesor partialUpdatedProfesor = new Profesor();
        partialUpdatedProfesor.setId(profesor.getId());

        partialUpdatedProfesor.profesorId(UPDATED_PROFESOR_ID).profesorName(UPDATED_PROFESOR_NAME);

        restProfesorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfesor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProfesor))
            )
            .andExpect(status().isOk());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeUpdate);
        Profesor testProfesor = profesorList.get(profesorList.size() - 1);
        assertThat(testProfesor.getProfesorId()).isEqualTo(UPDATED_PROFESOR_ID);
        assertThat(testProfesor.getProfesorName()).isEqualTo(UPDATED_PROFESOR_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingProfesor() throws Exception {
        int databaseSizeBeforeUpdate = profesorRepository.findAll().size();
        profesor.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfesorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, profesor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(profesor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProfesor() throws Exception {
        int databaseSizeBeforeUpdate = profesorRepository.findAll().size();
        profesor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfesorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(profesor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProfesor() throws Exception {
        int databaseSizeBeforeUpdate = profesorRepository.findAll().size();
        profesor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfesorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(profesor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProfesor() throws Exception {
        // Initialize the database
        profesorRepository.saveAndFlush(profesor);

        int databaseSizeBeforeDelete = profesorRepository.findAll().size();

        // Delete the profesor
        restProfesorMockMvc
            .perform(delete(ENTITY_API_URL_ID, profesor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
