package com.evaluar_cursos.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.evaluar_cursos.IntegrationTest;
import com.evaluar_cursos.domain.AcademicProgram;
import com.evaluar_cursos.repository.AcademicProgramRepository;
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
 * Integration tests for the {@link AcademicProgramResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AcademicProgramResourceIT {

    private static final UUID DEFAULT_PROGRAM_ID = UUID.randomUUID();
    private static final UUID UPDATED_PROGRAM_ID = UUID.randomUUID();

    private static final String DEFAULT_PROGRAM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROGRAM_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/academic-programs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AcademicProgramRepository academicProgramRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAcademicProgramMockMvc;

    private AcademicProgram academicProgram;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcademicProgram createEntity(EntityManager em) {
        AcademicProgram academicProgram = new AcademicProgram().programId(DEFAULT_PROGRAM_ID).programName(DEFAULT_PROGRAM_NAME);
        return academicProgram;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcademicProgram createUpdatedEntity(EntityManager em) {
        AcademicProgram academicProgram = new AcademicProgram().programId(UPDATED_PROGRAM_ID).programName(UPDATED_PROGRAM_NAME);
        return academicProgram;
    }

    @BeforeEach
    public void initTest() {
        academicProgram = createEntity(em);
    }

    @Test
    @Transactional
    void createAcademicProgram() throws Exception {
        int databaseSizeBeforeCreate = academicProgramRepository.findAll().size();
        // Create the AcademicProgram
        restAcademicProgramMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(academicProgram))
            )
            .andExpect(status().isCreated());

        // Validate the AcademicProgram in the database
        List<AcademicProgram> academicProgramList = academicProgramRepository.findAll();
        assertThat(academicProgramList).hasSize(databaseSizeBeforeCreate + 1);
        AcademicProgram testAcademicProgram = academicProgramList.get(academicProgramList.size() - 1);
        assertThat(testAcademicProgram.getProgramId()).isEqualTo(DEFAULT_PROGRAM_ID);
        assertThat(testAcademicProgram.getProgramName()).isEqualTo(DEFAULT_PROGRAM_NAME);
    }

    @Test
    @Transactional
    void createAcademicProgramWithExistingId() throws Exception {
        // Create the AcademicProgram with an existing ID
        academicProgram.setId(1L);

        int databaseSizeBeforeCreate = academicProgramRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcademicProgramMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(academicProgram))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcademicProgram in the database
        List<AcademicProgram> academicProgramList = academicProgramRepository.findAll();
        assertThat(academicProgramList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAcademicPrograms() throws Exception {
        // Initialize the database
        academicProgramRepository.saveAndFlush(academicProgram);

        // Get all the academicProgramList
        restAcademicProgramMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(academicProgram.getId().intValue())))
            .andExpect(jsonPath("$.[*].programId").value(hasItem(DEFAULT_PROGRAM_ID.toString())))
            .andExpect(jsonPath("$.[*].programName").value(hasItem(DEFAULT_PROGRAM_NAME)));
    }

    @Test
    @Transactional
    void getAcademicProgram() throws Exception {
        // Initialize the database
        academicProgramRepository.saveAndFlush(academicProgram);

        // Get the academicProgram
        restAcademicProgramMockMvc
            .perform(get(ENTITY_API_URL_ID, academicProgram.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(academicProgram.getId().intValue()))
            .andExpect(jsonPath("$.programId").value(DEFAULT_PROGRAM_ID.toString()))
            .andExpect(jsonPath("$.programName").value(DEFAULT_PROGRAM_NAME));
    }

    @Test
    @Transactional
    void getNonExistingAcademicProgram() throws Exception {
        // Get the academicProgram
        restAcademicProgramMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAcademicProgram() throws Exception {
        // Initialize the database
        academicProgramRepository.saveAndFlush(academicProgram);

        int databaseSizeBeforeUpdate = academicProgramRepository.findAll().size();

        // Update the academicProgram
        AcademicProgram updatedAcademicProgram = academicProgramRepository.findById(academicProgram.getId()).get();
        // Disconnect from session so that the updates on updatedAcademicProgram are not directly saved in db
        em.detach(updatedAcademicProgram);
        updatedAcademicProgram.programId(UPDATED_PROGRAM_ID).programName(UPDATED_PROGRAM_NAME);

        restAcademicProgramMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAcademicProgram.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAcademicProgram))
            )
            .andExpect(status().isOk());

        // Validate the AcademicProgram in the database
        List<AcademicProgram> academicProgramList = academicProgramRepository.findAll();
        assertThat(academicProgramList).hasSize(databaseSizeBeforeUpdate);
        AcademicProgram testAcademicProgram = academicProgramList.get(academicProgramList.size() - 1);
        assertThat(testAcademicProgram.getProgramId()).isEqualTo(UPDATED_PROGRAM_ID);
        assertThat(testAcademicProgram.getProgramName()).isEqualTo(UPDATED_PROGRAM_NAME);
    }

    @Test
    @Transactional
    void putNonExistingAcademicProgram() throws Exception {
        int databaseSizeBeforeUpdate = academicProgramRepository.findAll().size();
        academicProgram.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcademicProgramMockMvc
            .perform(
                put(ENTITY_API_URL_ID, academicProgram.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(academicProgram))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcademicProgram in the database
        List<AcademicProgram> academicProgramList = academicProgramRepository.findAll();
        assertThat(academicProgramList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAcademicProgram() throws Exception {
        int databaseSizeBeforeUpdate = academicProgramRepository.findAll().size();
        academicProgram.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcademicProgramMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(academicProgram))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcademicProgram in the database
        List<AcademicProgram> academicProgramList = academicProgramRepository.findAll();
        assertThat(academicProgramList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAcademicProgram() throws Exception {
        int databaseSizeBeforeUpdate = academicProgramRepository.findAll().size();
        academicProgram.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcademicProgramMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(academicProgram))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AcademicProgram in the database
        List<AcademicProgram> academicProgramList = academicProgramRepository.findAll();
        assertThat(academicProgramList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAcademicProgramWithPatch() throws Exception {
        // Initialize the database
        academicProgramRepository.saveAndFlush(academicProgram);

        int databaseSizeBeforeUpdate = academicProgramRepository.findAll().size();

        // Update the academicProgram using partial update
        AcademicProgram partialUpdatedAcademicProgram = new AcademicProgram();
        partialUpdatedAcademicProgram.setId(academicProgram.getId());

        restAcademicProgramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcademicProgram.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAcademicProgram))
            )
            .andExpect(status().isOk());

        // Validate the AcademicProgram in the database
        List<AcademicProgram> academicProgramList = academicProgramRepository.findAll();
        assertThat(academicProgramList).hasSize(databaseSizeBeforeUpdate);
        AcademicProgram testAcademicProgram = academicProgramList.get(academicProgramList.size() - 1);
        assertThat(testAcademicProgram.getProgramId()).isEqualTo(DEFAULT_PROGRAM_ID);
        assertThat(testAcademicProgram.getProgramName()).isEqualTo(DEFAULT_PROGRAM_NAME);
    }

    @Test
    @Transactional
    void fullUpdateAcademicProgramWithPatch() throws Exception {
        // Initialize the database
        academicProgramRepository.saveAndFlush(academicProgram);

        int databaseSizeBeforeUpdate = academicProgramRepository.findAll().size();

        // Update the academicProgram using partial update
        AcademicProgram partialUpdatedAcademicProgram = new AcademicProgram();
        partialUpdatedAcademicProgram.setId(academicProgram.getId());

        partialUpdatedAcademicProgram.programId(UPDATED_PROGRAM_ID).programName(UPDATED_PROGRAM_NAME);

        restAcademicProgramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcademicProgram.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAcademicProgram))
            )
            .andExpect(status().isOk());

        // Validate the AcademicProgram in the database
        List<AcademicProgram> academicProgramList = academicProgramRepository.findAll();
        assertThat(academicProgramList).hasSize(databaseSizeBeforeUpdate);
        AcademicProgram testAcademicProgram = academicProgramList.get(academicProgramList.size() - 1);
        assertThat(testAcademicProgram.getProgramId()).isEqualTo(UPDATED_PROGRAM_ID);
        assertThat(testAcademicProgram.getProgramName()).isEqualTo(UPDATED_PROGRAM_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingAcademicProgram() throws Exception {
        int databaseSizeBeforeUpdate = academicProgramRepository.findAll().size();
        academicProgram.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcademicProgramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, academicProgram.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(academicProgram))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcademicProgram in the database
        List<AcademicProgram> academicProgramList = academicProgramRepository.findAll();
        assertThat(academicProgramList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAcademicProgram() throws Exception {
        int databaseSizeBeforeUpdate = academicProgramRepository.findAll().size();
        academicProgram.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcademicProgramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(academicProgram))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcademicProgram in the database
        List<AcademicProgram> academicProgramList = academicProgramRepository.findAll();
        assertThat(academicProgramList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAcademicProgram() throws Exception {
        int databaseSizeBeforeUpdate = academicProgramRepository.findAll().size();
        academicProgram.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcademicProgramMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(academicProgram))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AcademicProgram in the database
        List<AcademicProgram> academicProgramList = academicProgramRepository.findAll();
        assertThat(academicProgramList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAcademicProgram() throws Exception {
        // Initialize the database
        academicProgramRepository.saveAndFlush(academicProgram);

        int databaseSizeBeforeDelete = academicProgramRepository.findAll().size();

        // Delete the academicProgram
        restAcademicProgramMockMvc
            .perform(delete(ENTITY_API_URL_ID, academicProgram.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AcademicProgram> academicProgramList = academicProgramRepository.findAll();
        assertThat(academicProgramList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
