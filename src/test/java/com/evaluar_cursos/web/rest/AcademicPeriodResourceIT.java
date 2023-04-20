package com.evaluar_cursos.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.evaluar_cursos.IntegrationTest;
import com.evaluar_cursos.domain.AcademicPeriod;
import com.evaluar_cursos.repository.AcademicPeriodRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
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
 * Integration tests for the {@link AcademicPeriodResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AcademicPeriodResourceIT {

    private static final Instant DEFAULT_INIT_PERIOD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INIT_PERIOD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FINISH_PERIOD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FINISH_PERIOD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/academic-periods";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AcademicPeriodRepository academicPeriodRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAcademicPeriodMockMvc;

    private AcademicPeriod academicPeriod;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcademicPeriod createEntity(EntityManager em) {
        AcademicPeriod academicPeriod = new AcademicPeriod().initPeriod(DEFAULT_INIT_PERIOD).finishPeriod(DEFAULT_FINISH_PERIOD);
        return academicPeriod;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcademicPeriod createUpdatedEntity(EntityManager em) {
        AcademicPeriod academicPeriod = new AcademicPeriod().initPeriod(UPDATED_INIT_PERIOD).finishPeriod(UPDATED_FINISH_PERIOD);
        return academicPeriod;
    }

    @BeforeEach
    public void initTest() {
        academicPeriod = createEntity(em);
    }

    @Test
    @Transactional
    void createAcademicPeriod() throws Exception {
        int databaseSizeBeforeCreate = academicPeriodRepository.findAll().size();
        // Create the AcademicPeriod
        restAcademicPeriodMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(academicPeriod))
            )
            .andExpect(status().isCreated());

        // Validate the AcademicPeriod in the database
        List<AcademicPeriod> academicPeriodList = academicPeriodRepository.findAll();
        assertThat(academicPeriodList).hasSize(databaseSizeBeforeCreate + 1);
        AcademicPeriod testAcademicPeriod = academicPeriodList.get(academicPeriodList.size() - 1);
        assertThat(testAcademicPeriod.getInitPeriod()).isEqualTo(DEFAULT_INIT_PERIOD);
        assertThat(testAcademicPeriod.getFinishPeriod()).isEqualTo(DEFAULT_FINISH_PERIOD);
    }

    @Test
    @Transactional
    void createAcademicPeriodWithExistingId() throws Exception {
        // Create the AcademicPeriod with an existing ID
        academicPeriod.setId(1L);

        int databaseSizeBeforeCreate = academicPeriodRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcademicPeriodMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(academicPeriod))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcademicPeriod in the database
        List<AcademicPeriod> academicPeriodList = academicPeriodRepository.findAll();
        assertThat(academicPeriodList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAcademicPeriods() throws Exception {
        // Initialize the database
        academicPeriodRepository.saveAndFlush(academicPeriod);

        // Get all the academicPeriodList
        restAcademicPeriodMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(academicPeriod.getId().intValue())))
            .andExpect(jsonPath("$.[*].initPeriod").value(hasItem(DEFAULT_INIT_PERIOD.toString())))
            .andExpect(jsonPath("$.[*].finishPeriod").value(hasItem(DEFAULT_FINISH_PERIOD.toString())));
    }

    @Test
    @Transactional
    void getAcademicPeriod() throws Exception {
        // Initialize the database
        academicPeriodRepository.saveAndFlush(academicPeriod);

        // Get the academicPeriod
        restAcademicPeriodMockMvc
            .perform(get(ENTITY_API_URL_ID, academicPeriod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(academicPeriod.getId().intValue()))
            .andExpect(jsonPath("$.initPeriod").value(DEFAULT_INIT_PERIOD.toString()))
            .andExpect(jsonPath("$.finishPeriod").value(DEFAULT_FINISH_PERIOD.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAcademicPeriod() throws Exception {
        // Get the academicPeriod
        restAcademicPeriodMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAcademicPeriod() throws Exception {
        // Initialize the database
        academicPeriodRepository.saveAndFlush(academicPeriod);

        int databaseSizeBeforeUpdate = academicPeriodRepository.findAll().size();

        // Update the academicPeriod
        AcademicPeriod updatedAcademicPeriod = academicPeriodRepository.findById(academicPeriod.getId()).get();
        // Disconnect from session so that the updates on updatedAcademicPeriod are not directly saved in db
        em.detach(updatedAcademicPeriod);
        updatedAcademicPeriod.initPeriod(UPDATED_INIT_PERIOD).finishPeriod(UPDATED_FINISH_PERIOD);

        restAcademicPeriodMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAcademicPeriod.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAcademicPeriod))
            )
            .andExpect(status().isOk());

        // Validate the AcademicPeriod in the database
        List<AcademicPeriod> academicPeriodList = academicPeriodRepository.findAll();
        assertThat(academicPeriodList).hasSize(databaseSizeBeforeUpdate);
        AcademicPeriod testAcademicPeriod = academicPeriodList.get(academicPeriodList.size() - 1);
        assertThat(testAcademicPeriod.getInitPeriod()).isEqualTo(UPDATED_INIT_PERIOD);
        assertThat(testAcademicPeriod.getFinishPeriod()).isEqualTo(UPDATED_FINISH_PERIOD);
    }

    @Test
    @Transactional
    void putNonExistingAcademicPeriod() throws Exception {
        int databaseSizeBeforeUpdate = academicPeriodRepository.findAll().size();
        academicPeriod.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcademicPeriodMockMvc
            .perform(
                put(ENTITY_API_URL_ID, academicPeriod.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(academicPeriod))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcademicPeriod in the database
        List<AcademicPeriod> academicPeriodList = academicPeriodRepository.findAll();
        assertThat(academicPeriodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAcademicPeriod() throws Exception {
        int databaseSizeBeforeUpdate = academicPeriodRepository.findAll().size();
        academicPeriod.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcademicPeriodMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(academicPeriod))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcademicPeriod in the database
        List<AcademicPeriod> academicPeriodList = academicPeriodRepository.findAll();
        assertThat(academicPeriodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAcademicPeriod() throws Exception {
        int databaseSizeBeforeUpdate = academicPeriodRepository.findAll().size();
        academicPeriod.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcademicPeriodMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(academicPeriod)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AcademicPeriod in the database
        List<AcademicPeriod> academicPeriodList = academicPeriodRepository.findAll();
        assertThat(academicPeriodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAcademicPeriodWithPatch() throws Exception {
        // Initialize the database
        academicPeriodRepository.saveAndFlush(academicPeriod);

        int databaseSizeBeforeUpdate = academicPeriodRepository.findAll().size();

        // Update the academicPeriod using partial update
        AcademicPeriod partialUpdatedAcademicPeriod = new AcademicPeriod();
        partialUpdatedAcademicPeriod.setId(academicPeriod.getId());

        partialUpdatedAcademicPeriod.initPeriod(UPDATED_INIT_PERIOD).finishPeriod(UPDATED_FINISH_PERIOD);

        restAcademicPeriodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcademicPeriod.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAcademicPeriod))
            )
            .andExpect(status().isOk());

        // Validate the AcademicPeriod in the database
        List<AcademicPeriod> academicPeriodList = academicPeriodRepository.findAll();
        assertThat(academicPeriodList).hasSize(databaseSizeBeforeUpdate);
        AcademicPeriod testAcademicPeriod = academicPeriodList.get(academicPeriodList.size() - 1);
        assertThat(testAcademicPeriod.getInitPeriod()).isEqualTo(UPDATED_INIT_PERIOD);
        assertThat(testAcademicPeriod.getFinishPeriod()).isEqualTo(UPDATED_FINISH_PERIOD);
    }

    @Test
    @Transactional
    void fullUpdateAcademicPeriodWithPatch() throws Exception {
        // Initialize the database
        academicPeriodRepository.saveAndFlush(academicPeriod);

        int databaseSizeBeforeUpdate = academicPeriodRepository.findAll().size();

        // Update the academicPeriod using partial update
        AcademicPeriod partialUpdatedAcademicPeriod = new AcademicPeriod();
        partialUpdatedAcademicPeriod.setId(academicPeriod.getId());

        partialUpdatedAcademicPeriod.initPeriod(UPDATED_INIT_PERIOD).finishPeriod(UPDATED_FINISH_PERIOD);

        restAcademicPeriodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcademicPeriod.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAcademicPeriod))
            )
            .andExpect(status().isOk());

        // Validate the AcademicPeriod in the database
        List<AcademicPeriod> academicPeriodList = academicPeriodRepository.findAll();
        assertThat(academicPeriodList).hasSize(databaseSizeBeforeUpdate);
        AcademicPeriod testAcademicPeriod = academicPeriodList.get(academicPeriodList.size() - 1);
        assertThat(testAcademicPeriod.getInitPeriod()).isEqualTo(UPDATED_INIT_PERIOD);
        assertThat(testAcademicPeriod.getFinishPeriod()).isEqualTo(UPDATED_FINISH_PERIOD);
    }

    @Test
    @Transactional
    void patchNonExistingAcademicPeriod() throws Exception {
        int databaseSizeBeforeUpdate = academicPeriodRepository.findAll().size();
        academicPeriod.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcademicPeriodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, academicPeriod.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(academicPeriod))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcademicPeriod in the database
        List<AcademicPeriod> academicPeriodList = academicPeriodRepository.findAll();
        assertThat(academicPeriodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAcademicPeriod() throws Exception {
        int databaseSizeBeforeUpdate = academicPeriodRepository.findAll().size();
        academicPeriod.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcademicPeriodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(academicPeriod))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcademicPeriod in the database
        List<AcademicPeriod> academicPeriodList = academicPeriodRepository.findAll();
        assertThat(academicPeriodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAcademicPeriod() throws Exception {
        int databaseSizeBeforeUpdate = academicPeriodRepository.findAll().size();
        academicPeriod.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcademicPeriodMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(academicPeriod))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AcademicPeriod in the database
        List<AcademicPeriod> academicPeriodList = academicPeriodRepository.findAll();
        assertThat(academicPeriodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAcademicPeriod() throws Exception {
        // Initialize the database
        academicPeriodRepository.saveAndFlush(academicPeriod);

        int databaseSizeBeforeDelete = academicPeriodRepository.findAll().size();

        // Delete the academicPeriod
        restAcademicPeriodMockMvc
            .perform(delete(ENTITY_API_URL_ID, academicPeriod.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AcademicPeriod> academicPeriodList = academicPeriodRepository.findAll();
        assertThat(academicPeriodList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
