package com.evaluar_cursos.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.evaluar_cursos.IntegrationTest;
import com.evaluar_cursos.domain.TotalScoreProfesor;
import com.evaluar_cursos.repository.TotalScoreProfesorRepository;
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
 * Integration tests for the {@link TotalScoreProfesorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TotalScoreProfesorResourceIT {

    private static final Integer DEFAULT_P_AVERAGE_ONE = 1;
    private static final Integer UPDATED_P_AVERAGE_ONE = 2;

    private static final Integer DEFAULT_P_AVERAGE_TWO = 1;
    private static final Integer UPDATED_P_AVERAGE_TWO = 2;

    private static final Integer DEFAULT_P_AVERAGE_THREE = 1;
    private static final Integer UPDATED_P_AVERAGE_THREE = 2;

    private static final Integer DEFAULT_P_AVERAGE_FOUR = 1;
    private static final Integer UPDATED_P_AVERAGE_FOUR = 2;

    private static final Integer DEFAULT_P_AVERAGE_FIVE = 1;
    private static final Integer UPDATED_P_AVERAGE_FIVE = 2;

    private static final String ENTITY_API_URL = "/api/total-score-profesors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TotalScoreProfesorRepository totalScoreProfesorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTotalScoreProfesorMockMvc;

    private TotalScoreProfesor totalScoreProfesor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TotalScoreProfesor createEntity(EntityManager em) {
        TotalScoreProfesor totalScoreProfesor = new TotalScoreProfesor()
            .pAverageOne(DEFAULT_P_AVERAGE_ONE)
            .pAverageTwo(DEFAULT_P_AVERAGE_TWO)
            .pAverageThree(DEFAULT_P_AVERAGE_THREE)
            .pAverageFour(DEFAULT_P_AVERAGE_FOUR)
            .pAverageFive(DEFAULT_P_AVERAGE_FIVE);
        return totalScoreProfesor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TotalScoreProfesor createUpdatedEntity(EntityManager em) {
        TotalScoreProfesor totalScoreProfesor = new TotalScoreProfesor()
            .pAverageOne(UPDATED_P_AVERAGE_ONE)
            .pAverageTwo(UPDATED_P_AVERAGE_TWO)
            .pAverageThree(UPDATED_P_AVERAGE_THREE)
            .pAverageFour(UPDATED_P_AVERAGE_FOUR)
            .pAverageFive(UPDATED_P_AVERAGE_FIVE);
        return totalScoreProfesor;
    }

    @BeforeEach
    public void initTest() {
        totalScoreProfesor = createEntity(em);
    }

    @Test
    @Transactional
    void createTotalScoreProfesor() throws Exception {
        int databaseSizeBeforeCreate = totalScoreProfesorRepository.findAll().size();
        // Create the TotalScoreProfesor
        restTotalScoreProfesorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(totalScoreProfesor))
            )
            .andExpect(status().isCreated());

        // Validate the TotalScoreProfesor in the database
        List<TotalScoreProfesor> totalScoreProfesorList = totalScoreProfesorRepository.findAll();
        assertThat(totalScoreProfesorList).hasSize(databaseSizeBeforeCreate + 1);
        TotalScoreProfesor testTotalScoreProfesor = totalScoreProfesorList.get(totalScoreProfesorList.size() - 1);
        assertThat(testTotalScoreProfesor.getpAverageOne()).isEqualTo(DEFAULT_P_AVERAGE_ONE);
        assertThat(testTotalScoreProfesor.getpAverageTwo()).isEqualTo(DEFAULT_P_AVERAGE_TWO);
        assertThat(testTotalScoreProfesor.getpAverageThree()).isEqualTo(DEFAULT_P_AVERAGE_THREE);
        assertThat(testTotalScoreProfesor.getpAverageFour()).isEqualTo(DEFAULT_P_AVERAGE_FOUR);
        assertThat(testTotalScoreProfesor.getpAverageFive()).isEqualTo(DEFAULT_P_AVERAGE_FIVE);
    }

    @Test
    @Transactional
    void createTotalScoreProfesorWithExistingId() throws Exception {
        // Create the TotalScoreProfesor with an existing ID
        totalScoreProfesor.setId(1L);

        int databaseSizeBeforeCreate = totalScoreProfesorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTotalScoreProfesorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(totalScoreProfesor))
            )
            .andExpect(status().isBadRequest());

        // Validate the TotalScoreProfesor in the database
        List<TotalScoreProfesor> totalScoreProfesorList = totalScoreProfesorRepository.findAll();
        assertThat(totalScoreProfesorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTotalScoreProfesors() throws Exception {
        // Initialize the database
        totalScoreProfesorRepository.saveAndFlush(totalScoreProfesor);

        // Get all the totalScoreProfesorList
        restTotalScoreProfesorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(totalScoreProfesor.getId().intValue())))
            .andExpect(jsonPath("$.[*].pAverageOne").value(hasItem(DEFAULT_P_AVERAGE_ONE)))
            .andExpect(jsonPath("$.[*].pAverageTwo").value(hasItem(DEFAULT_P_AVERAGE_TWO)))
            .andExpect(jsonPath("$.[*].pAverageThree").value(hasItem(DEFAULT_P_AVERAGE_THREE)))
            .andExpect(jsonPath("$.[*].pAverageFour").value(hasItem(DEFAULT_P_AVERAGE_FOUR)))
            .andExpect(jsonPath("$.[*].pAverageFive").value(hasItem(DEFAULT_P_AVERAGE_FIVE)));
    }

    @Test
    @Transactional
    void getTotalScoreProfesor() throws Exception {
        // Initialize the database
        totalScoreProfesorRepository.saveAndFlush(totalScoreProfesor);

        // Get the totalScoreProfesor
        restTotalScoreProfesorMockMvc
            .perform(get(ENTITY_API_URL_ID, totalScoreProfesor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(totalScoreProfesor.getId().intValue()))
            .andExpect(jsonPath("$.pAverageOne").value(DEFAULT_P_AVERAGE_ONE))
            .andExpect(jsonPath("$.pAverageTwo").value(DEFAULT_P_AVERAGE_TWO))
            .andExpect(jsonPath("$.pAverageThree").value(DEFAULT_P_AVERAGE_THREE))
            .andExpect(jsonPath("$.pAverageFour").value(DEFAULT_P_AVERAGE_FOUR))
            .andExpect(jsonPath("$.pAverageFive").value(DEFAULT_P_AVERAGE_FIVE));
    }

    @Test
    @Transactional
    void getNonExistingTotalScoreProfesor() throws Exception {
        // Get the totalScoreProfesor
        restTotalScoreProfesorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTotalScoreProfesor() throws Exception {
        // Initialize the database
        totalScoreProfesorRepository.saveAndFlush(totalScoreProfesor);

        int databaseSizeBeforeUpdate = totalScoreProfesorRepository.findAll().size();

        // Update the totalScoreProfesor
        TotalScoreProfesor updatedTotalScoreProfesor = totalScoreProfesorRepository.findById(totalScoreProfesor.getId()).get();
        // Disconnect from session so that the updates on updatedTotalScoreProfesor are not directly saved in db
        em.detach(updatedTotalScoreProfesor);
        updatedTotalScoreProfesor
            .pAverageOne(UPDATED_P_AVERAGE_ONE)
            .pAverageTwo(UPDATED_P_AVERAGE_TWO)
            .pAverageThree(UPDATED_P_AVERAGE_THREE)
            .pAverageFour(UPDATED_P_AVERAGE_FOUR)
            .pAverageFive(UPDATED_P_AVERAGE_FIVE);

        restTotalScoreProfesorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTotalScoreProfesor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTotalScoreProfesor))
            )
            .andExpect(status().isOk());

        // Validate the TotalScoreProfesor in the database
        List<TotalScoreProfesor> totalScoreProfesorList = totalScoreProfesorRepository.findAll();
        assertThat(totalScoreProfesorList).hasSize(databaseSizeBeforeUpdate);
        TotalScoreProfesor testTotalScoreProfesor = totalScoreProfesorList.get(totalScoreProfesorList.size() - 1);
        assertThat(testTotalScoreProfesor.getpAverageOne()).isEqualTo(UPDATED_P_AVERAGE_ONE);
        assertThat(testTotalScoreProfesor.getpAverageTwo()).isEqualTo(UPDATED_P_AVERAGE_TWO);
        assertThat(testTotalScoreProfesor.getpAverageThree()).isEqualTo(UPDATED_P_AVERAGE_THREE);
        assertThat(testTotalScoreProfesor.getpAverageFour()).isEqualTo(UPDATED_P_AVERAGE_FOUR);
        assertThat(testTotalScoreProfesor.getpAverageFive()).isEqualTo(UPDATED_P_AVERAGE_FIVE);
    }

    @Test
    @Transactional
    void putNonExistingTotalScoreProfesor() throws Exception {
        int databaseSizeBeforeUpdate = totalScoreProfesorRepository.findAll().size();
        totalScoreProfesor.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTotalScoreProfesorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, totalScoreProfesor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(totalScoreProfesor))
            )
            .andExpect(status().isBadRequest());

        // Validate the TotalScoreProfesor in the database
        List<TotalScoreProfesor> totalScoreProfesorList = totalScoreProfesorRepository.findAll();
        assertThat(totalScoreProfesorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTotalScoreProfesor() throws Exception {
        int databaseSizeBeforeUpdate = totalScoreProfesorRepository.findAll().size();
        totalScoreProfesor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTotalScoreProfesorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(totalScoreProfesor))
            )
            .andExpect(status().isBadRequest());

        // Validate the TotalScoreProfesor in the database
        List<TotalScoreProfesor> totalScoreProfesorList = totalScoreProfesorRepository.findAll();
        assertThat(totalScoreProfesorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTotalScoreProfesor() throws Exception {
        int databaseSizeBeforeUpdate = totalScoreProfesorRepository.findAll().size();
        totalScoreProfesor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTotalScoreProfesorMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(totalScoreProfesor))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TotalScoreProfesor in the database
        List<TotalScoreProfesor> totalScoreProfesorList = totalScoreProfesorRepository.findAll();
        assertThat(totalScoreProfesorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTotalScoreProfesorWithPatch() throws Exception {
        // Initialize the database
        totalScoreProfesorRepository.saveAndFlush(totalScoreProfesor);

        int databaseSizeBeforeUpdate = totalScoreProfesorRepository.findAll().size();

        // Update the totalScoreProfesor using partial update
        TotalScoreProfesor partialUpdatedTotalScoreProfesor = new TotalScoreProfesor();
        partialUpdatedTotalScoreProfesor.setId(totalScoreProfesor.getId());

        partialUpdatedTotalScoreProfesor.pAverageOne(UPDATED_P_AVERAGE_ONE).pAverageTwo(UPDATED_P_AVERAGE_TWO);

        restTotalScoreProfesorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTotalScoreProfesor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTotalScoreProfesor))
            )
            .andExpect(status().isOk());

        // Validate the TotalScoreProfesor in the database
        List<TotalScoreProfesor> totalScoreProfesorList = totalScoreProfesorRepository.findAll();
        assertThat(totalScoreProfesorList).hasSize(databaseSizeBeforeUpdate);
        TotalScoreProfesor testTotalScoreProfesor = totalScoreProfesorList.get(totalScoreProfesorList.size() - 1);
        assertThat(testTotalScoreProfesor.getpAverageOne()).isEqualTo(UPDATED_P_AVERAGE_ONE);
        assertThat(testTotalScoreProfesor.getpAverageTwo()).isEqualTo(UPDATED_P_AVERAGE_TWO);
        assertThat(testTotalScoreProfesor.getpAverageThree()).isEqualTo(DEFAULT_P_AVERAGE_THREE);
        assertThat(testTotalScoreProfesor.getpAverageFour()).isEqualTo(DEFAULT_P_AVERAGE_FOUR);
        assertThat(testTotalScoreProfesor.getpAverageFive()).isEqualTo(DEFAULT_P_AVERAGE_FIVE);
    }

    @Test
    @Transactional
    void fullUpdateTotalScoreProfesorWithPatch() throws Exception {
        // Initialize the database
        totalScoreProfesorRepository.saveAndFlush(totalScoreProfesor);

        int databaseSizeBeforeUpdate = totalScoreProfesorRepository.findAll().size();

        // Update the totalScoreProfesor using partial update
        TotalScoreProfesor partialUpdatedTotalScoreProfesor = new TotalScoreProfesor();
        partialUpdatedTotalScoreProfesor.setId(totalScoreProfesor.getId());

        partialUpdatedTotalScoreProfesor
            .pAverageOne(UPDATED_P_AVERAGE_ONE)
            .pAverageTwo(UPDATED_P_AVERAGE_TWO)
            .pAverageThree(UPDATED_P_AVERAGE_THREE)
            .pAverageFour(UPDATED_P_AVERAGE_FOUR)
            .pAverageFive(UPDATED_P_AVERAGE_FIVE);

        restTotalScoreProfesorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTotalScoreProfesor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTotalScoreProfesor))
            )
            .andExpect(status().isOk());

        // Validate the TotalScoreProfesor in the database
        List<TotalScoreProfesor> totalScoreProfesorList = totalScoreProfesorRepository.findAll();
        assertThat(totalScoreProfesorList).hasSize(databaseSizeBeforeUpdate);
        TotalScoreProfesor testTotalScoreProfesor = totalScoreProfesorList.get(totalScoreProfesorList.size() - 1);
        assertThat(testTotalScoreProfesor.getpAverageOne()).isEqualTo(UPDATED_P_AVERAGE_ONE);
        assertThat(testTotalScoreProfesor.getpAverageTwo()).isEqualTo(UPDATED_P_AVERAGE_TWO);
        assertThat(testTotalScoreProfesor.getpAverageThree()).isEqualTo(UPDATED_P_AVERAGE_THREE);
        assertThat(testTotalScoreProfesor.getpAverageFour()).isEqualTo(UPDATED_P_AVERAGE_FOUR);
        assertThat(testTotalScoreProfesor.getpAverageFive()).isEqualTo(UPDATED_P_AVERAGE_FIVE);
    }

    @Test
    @Transactional
    void patchNonExistingTotalScoreProfesor() throws Exception {
        int databaseSizeBeforeUpdate = totalScoreProfesorRepository.findAll().size();
        totalScoreProfesor.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTotalScoreProfesorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, totalScoreProfesor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(totalScoreProfesor))
            )
            .andExpect(status().isBadRequest());

        // Validate the TotalScoreProfesor in the database
        List<TotalScoreProfesor> totalScoreProfesorList = totalScoreProfesorRepository.findAll();
        assertThat(totalScoreProfesorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTotalScoreProfesor() throws Exception {
        int databaseSizeBeforeUpdate = totalScoreProfesorRepository.findAll().size();
        totalScoreProfesor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTotalScoreProfesorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(totalScoreProfesor))
            )
            .andExpect(status().isBadRequest());

        // Validate the TotalScoreProfesor in the database
        List<TotalScoreProfesor> totalScoreProfesorList = totalScoreProfesorRepository.findAll();
        assertThat(totalScoreProfesorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTotalScoreProfesor() throws Exception {
        int databaseSizeBeforeUpdate = totalScoreProfesorRepository.findAll().size();
        totalScoreProfesor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTotalScoreProfesorMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(totalScoreProfesor))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TotalScoreProfesor in the database
        List<TotalScoreProfesor> totalScoreProfesorList = totalScoreProfesorRepository.findAll();
        assertThat(totalScoreProfesorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTotalScoreProfesor() throws Exception {
        // Initialize the database
        totalScoreProfesorRepository.saveAndFlush(totalScoreProfesor);

        int databaseSizeBeforeDelete = totalScoreProfesorRepository.findAll().size();

        // Delete the totalScoreProfesor
        restTotalScoreProfesorMockMvc
            .perform(delete(ENTITY_API_URL_ID, totalScoreProfesor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TotalScoreProfesor> totalScoreProfesorList = totalScoreProfesorRepository.findAll();
        assertThat(totalScoreProfesorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
