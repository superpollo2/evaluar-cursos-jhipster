package com.evaluar_cursos.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.evaluar_cursos.IntegrationTest;
import com.evaluar_cursos.domain.TotalScoreCourse;
import com.evaluar_cursos.repository.TotalScoreCourseRepository;
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
 * Integration tests for the {@link TotalScoreCourseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TotalScoreCourseResourceIT {

    private static final Integer DEFAULT_C_AVERAGE_ONE = 1;
    private static final Integer UPDATED_C_AVERAGE_ONE = 2;

    private static final Integer DEFAULT_C_AVERAGE_TWO = 1;
    private static final Integer UPDATED_C_AVERAGE_TWO = 2;

    private static final Integer DEFAULT_C_AVERAGE_THREE = 1;
    private static final Integer UPDATED_C_AVERAGE_THREE = 2;

    private static final Integer DEFAULT_C_AVERAGE_FOUR = 1;
    private static final Integer UPDATED_C_AVERAGE_FOUR = 2;

    private static final Integer DEFAULT_C_AVERAGE_FIVE = 1;
    private static final Integer UPDATED_C_AVERAGE_FIVE = 2;

    private static final String ENTITY_API_URL = "/api/total-score-courses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TotalScoreCourseRepository totalScoreCourseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTotalScoreCourseMockMvc;

    private TotalScoreCourse totalScoreCourse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TotalScoreCourse createEntity(EntityManager em) {
        TotalScoreCourse totalScoreCourse = new TotalScoreCourse()
            .cAverageOne(DEFAULT_C_AVERAGE_ONE)
            .cAverageTwo(DEFAULT_C_AVERAGE_TWO)
            .cAverageThree(DEFAULT_C_AVERAGE_THREE)
            .cAverageFour(DEFAULT_C_AVERAGE_FOUR)
            .cAverageFive(DEFAULT_C_AVERAGE_FIVE);
        return totalScoreCourse;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TotalScoreCourse createUpdatedEntity(EntityManager em) {
        TotalScoreCourse totalScoreCourse = new TotalScoreCourse()
            .cAverageOne(UPDATED_C_AVERAGE_ONE)
            .cAverageTwo(UPDATED_C_AVERAGE_TWO)
            .cAverageThree(UPDATED_C_AVERAGE_THREE)
            .cAverageFour(UPDATED_C_AVERAGE_FOUR)
            .cAverageFive(UPDATED_C_AVERAGE_FIVE);
        return totalScoreCourse;
    }

    @BeforeEach
    public void initTest() {
        totalScoreCourse = createEntity(em);
    }

    @Test
    @Transactional
    void createTotalScoreCourse() throws Exception {
        int databaseSizeBeforeCreate = totalScoreCourseRepository.findAll().size();
        // Create the TotalScoreCourse
        restTotalScoreCourseMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(totalScoreCourse))
            )
            .andExpect(status().isCreated());

        // Validate the TotalScoreCourse in the database
        List<TotalScoreCourse> totalScoreCourseList = totalScoreCourseRepository.findAll();
        assertThat(totalScoreCourseList).hasSize(databaseSizeBeforeCreate + 1);
        TotalScoreCourse testTotalScoreCourse = totalScoreCourseList.get(totalScoreCourseList.size() - 1);
        assertThat(testTotalScoreCourse.getcAverageOne()).isEqualTo(DEFAULT_C_AVERAGE_ONE);
        assertThat(testTotalScoreCourse.getcAverageTwo()).isEqualTo(DEFAULT_C_AVERAGE_TWO);
        assertThat(testTotalScoreCourse.getcAverageThree()).isEqualTo(DEFAULT_C_AVERAGE_THREE);
        assertThat(testTotalScoreCourse.getcAverageFour()).isEqualTo(DEFAULT_C_AVERAGE_FOUR);
        assertThat(testTotalScoreCourse.getcAverageFive()).isEqualTo(DEFAULT_C_AVERAGE_FIVE);
    }

    @Test
    @Transactional
    void createTotalScoreCourseWithExistingId() throws Exception {
        // Create the TotalScoreCourse with an existing ID
        totalScoreCourse.setId(1L);

        int databaseSizeBeforeCreate = totalScoreCourseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTotalScoreCourseMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(totalScoreCourse))
            )
            .andExpect(status().isBadRequest());

        // Validate the TotalScoreCourse in the database
        List<TotalScoreCourse> totalScoreCourseList = totalScoreCourseRepository.findAll();
        assertThat(totalScoreCourseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTotalScoreCourses() throws Exception {
        // Initialize the database
        totalScoreCourseRepository.saveAndFlush(totalScoreCourse);

        // Get all the totalScoreCourseList
        restTotalScoreCourseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(totalScoreCourse.getId().intValue())))
            .andExpect(jsonPath("$.[*].cAverageOne").value(hasItem(DEFAULT_C_AVERAGE_ONE)))
            .andExpect(jsonPath("$.[*].cAverageTwo").value(hasItem(DEFAULT_C_AVERAGE_TWO)))
            .andExpect(jsonPath("$.[*].cAverageThree").value(hasItem(DEFAULT_C_AVERAGE_THREE)))
            .andExpect(jsonPath("$.[*].cAverageFour").value(hasItem(DEFAULT_C_AVERAGE_FOUR)))
            .andExpect(jsonPath("$.[*].cAverageFive").value(hasItem(DEFAULT_C_AVERAGE_FIVE)));
    }

    @Test
    @Transactional
    void getTotalScoreCourse() throws Exception {
        // Initialize the database
        totalScoreCourseRepository.saveAndFlush(totalScoreCourse);

        // Get the totalScoreCourse
        restTotalScoreCourseMockMvc
            .perform(get(ENTITY_API_URL_ID, totalScoreCourse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(totalScoreCourse.getId().intValue()))
            .andExpect(jsonPath("$.cAverageOne").value(DEFAULT_C_AVERAGE_ONE))
            .andExpect(jsonPath("$.cAverageTwo").value(DEFAULT_C_AVERAGE_TWO))
            .andExpect(jsonPath("$.cAverageThree").value(DEFAULT_C_AVERAGE_THREE))
            .andExpect(jsonPath("$.cAverageFour").value(DEFAULT_C_AVERAGE_FOUR))
            .andExpect(jsonPath("$.cAverageFive").value(DEFAULT_C_AVERAGE_FIVE));
    }

    @Test
    @Transactional
    void getNonExistingTotalScoreCourse() throws Exception {
        // Get the totalScoreCourse
        restTotalScoreCourseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTotalScoreCourse() throws Exception {
        // Initialize the database
        totalScoreCourseRepository.saveAndFlush(totalScoreCourse);

        int databaseSizeBeforeUpdate = totalScoreCourseRepository.findAll().size();

        // Update the totalScoreCourse
        TotalScoreCourse updatedTotalScoreCourse = totalScoreCourseRepository.findById(totalScoreCourse.getId()).get();
        // Disconnect from session so that the updates on updatedTotalScoreCourse are not directly saved in db
        em.detach(updatedTotalScoreCourse);
        updatedTotalScoreCourse
            .cAverageOne(UPDATED_C_AVERAGE_ONE)
            .cAverageTwo(UPDATED_C_AVERAGE_TWO)
            .cAverageThree(UPDATED_C_AVERAGE_THREE)
            .cAverageFour(UPDATED_C_AVERAGE_FOUR)
            .cAverageFive(UPDATED_C_AVERAGE_FIVE);

        restTotalScoreCourseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTotalScoreCourse.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTotalScoreCourse))
            )
            .andExpect(status().isOk());

        // Validate the TotalScoreCourse in the database
        List<TotalScoreCourse> totalScoreCourseList = totalScoreCourseRepository.findAll();
        assertThat(totalScoreCourseList).hasSize(databaseSizeBeforeUpdate);
        TotalScoreCourse testTotalScoreCourse = totalScoreCourseList.get(totalScoreCourseList.size() - 1);
        assertThat(testTotalScoreCourse.getcAverageOne()).isEqualTo(UPDATED_C_AVERAGE_ONE);
        assertThat(testTotalScoreCourse.getcAverageTwo()).isEqualTo(UPDATED_C_AVERAGE_TWO);
        assertThat(testTotalScoreCourse.getcAverageThree()).isEqualTo(UPDATED_C_AVERAGE_THREE);
        assertThat(testTotalScoreCourse.getcAverageFour()).isEqualTo(UPDATED_C_AVERAGE_FOUR);
        assertThat(testTotalScoreCourse.getcAverageFive()).isEqualTo(UPDATED_C_AVERAGE_FIVE);
    }

    @Test
    @Transactional
    void putNonExistingTotalScoreCourse() throws Exception {
        int databaseSizeBeforeUpdate = totalScoreCourseRepository.findAll().size();
        totalScoreCourse.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTotalScoreCourseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, totalScoreCourse.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(totalScoreCourse))
            )
            .andExpect(status().isBadRequest());

        // Validate the TotalScoreCourse in the database
        List<TotalScoreCourse> totalScoreCourseList = totalScoreCourseRepository.findAll();
        assertThat(totalScoreCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTotalScoreCourse() throws Exception {
        int databaseSizeBeforeUpdate = totalScoreCourseRepository.findAll().size();
        totalScoreCourse.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTotalScoreCourseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(totalScoreCourse))
            )
            .andExpect(status().isBadRequest());

        // Validate the TotalScoreCourse in the database
        List<TotalScoreCourse> totalScoreCourseList = totalScoreCourseRepository.findAll();
        assertThat(totalScoreCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTotalScoreCourse() throws Exception {
        int databaseSizeBeforeUpdate = totalScoreCourseRepository.findAll().size();
        totalScoreCourse.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTotalScoreCourseMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(totalScoreCourse))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TotalScoreCourse in the database
        List<TotalScoreCourse> totalScoreCourseList = totalScoreCourseRepository.findAll();
        assertThat(totalScoreCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTotalScoreCourseWithPatch() throws Exception {
        // Initialize the database
        totalScoreCourseRepository.saveAndFlush(totalScoreCourse);

        int databaseSizeBeforeUpdate = totalScoreCourseRepository.findAll().size();

        // Update the totalScoreCourse using partial update
        TotalScoreCourse partialUpdatedTotalScoreCourse = new TotalScoreCourse();
        partialUpdatedTotalScoreCourse.setId(totalScoreCourse.getId());

        partialUpdatedTotalScoreCourse.cAverageTwo(UPDATED_C_AVERAGE_TWO).cAverageFour(UPDATED_C_AVERAGE_FOUR);

        restTotalScoreCourseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTotalScoreCourse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTotalScoreCourse))
            )
            .andExpect(status().isOk());

        // Validate the TotalScoreCourse in the database
        List<TotalScoreCourse> totalScoreCourseList = totalScoreCourseRepository.findAll();
        assertThat(totalScoreCourseList).hasSize(databaseSizeBeforeUpdate);
        TotalScoreCourse testTotalScoreCourse = totalScoreCourseList.get(totalScoreCourseList.size() - 1);
        assertThat(testTotalScoreCourse.getcAverageOne()).isEqualTo(DEFAULT_C_AVERAGE_ONE);
        assertThat(testTotalScoreCourse.getcAverageTwo()).isEqualTo(UPDATED_C_AVERAGE_TWO);
        assertThat(testTotalScoreCourse.getcAverageThree()).isEqualTo(DEFAULT_C_AVERAGE_THREE);
        assertThat(testTotalScoreCourse.getcAverageFour()).isEqualTo(UPDATED_C_AVERAGE_FOUR);
        assertThat(testTotalScoreCourse.getcAverageFive()).isEqualTo(DEFAULT_C_AVERAGE_FIVE);
    }

    @Test
    @Transactional
    void fullUpdateTotalScoreCourseWithPatch() throws Exception {
        // Initialize the database
        totalScoreCourseRepository.saveAndFlush(totalScoreCourse);

        int databaseSizeBeforeUpdate = totalScoreCourseRepository.findAll().size();

        // Update the totalScoreCourse using partial update
        TotalScoreCourse partialUpdatedTotalScoreCourse = new TotalScoreCourse();
        partialUpdatedTotalScoreCourse.setId(totalScoreCourse.getId());

        partialUpdatedTotalScoreCourse
            .cAverageOne(UPDATED_C_AVERAGE_ONE)
            .cAverageTwo(UPDATED_C_AVERAGE_TWO)
            .cAverageThree(UPDATED_C_AVERAGE_THREE)
            .cAverageFour(UPDATED_C_AVERAGE_FOUR)
            .cAverageFive(UPDATED_C_AVERAGE_FIVE);

        restTotalScoreCourseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTotalScoreCourse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTotalScoreCourse))
            )
            .andExpect(status().isOk());

        // Validate the TotalScoreCourse in the database
        List<TotalScoreCourse> totalScoreCourseList = totalScoreCourseRepository.findAll();
        assertThat(totalScoreCourseList).hasSize(databaseSizeBeforeUpdate);
        TotalScoreCourse testTotalScoreCourse = totalScoreCourseList.get(totalScoreCourseList.size() - 1);
        assertThat(testTotalScoreCourse.getcAverageOne()).isEqualTo(UPDATED_C_AVERAGE_ONE);
        assertThat(testTotalScoreCourse.getcAverageTwo()).isEqualTo(UPDATED_C_AVERAGE_TWO);
        assertThat(testTotalScoreCourse.getcAverageThree()).isEqualTo(UPDATED_C_AVERAGE_THREE);
        assertThat(testTotalScoreCourse.getcAverageFour()).isEqualTo(UPDATED_C_AVERAGE_FOUR);
        assertThat(testTotalScoreCourse.getcAverageFive()).isEqualTo(UPDATED_C_AVERAGE_FIVE);
    }

    @Test
    @Transactional
    void patchNonExistingTotalScoreCourse() throws Exception {
        int databaseSizeBeforeUpdate = totalScoreCourseRepository.findAll().size();
        totalScoreCourse.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTotalScoreCourseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, totalScoreCourse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(totalScoreCourse))
            )
            .andExpect(status().isBadRequest());

        // Validate the TotalScoreCourse in the database
        List<TotalScoreCourse> totalScoreCourseList = totalScoreCourseRepository.findAll();
        assertThat(totalScoreCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTotalScoreCourse() throws Exception {
        int databaseSizeBeforeUpdate = totalScoreCourseRepository.findAll().size();
        totalScoreCourse.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTotalScoreCourseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(totalScoreCourse))
            )
            .andExpect(status().isBadRequest());

        // Validate the TotalScoreCourse in the database
        List<TotalScoreCourse> totalScoreCourseList = totalScoreCourseRepository.findAll();
        assertThat(totalScoreCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTotalScoreCourse() throws Exception {
        int databaseSizeBeforeUpdate = totalScoreCourseRepository.findAll().size();
        totalScoreCourse.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTotalScoreCourseMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(totalScoreCourse))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TotalScoreCourse in the database
        List<TotalScoreCourse> totalScoreCourseList = totalScoreCourseRepository.findAll();
        assertThat(totalScoreCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTotalScoreCourse() throws Exception {
        // Initialize the database
        totalScoreCourseRepository.saveAndFlush(totalScoreCourse);

        int databaseSizeBeforeDelete = totalScoreCourseRepository.findAll().size();

        // Delete the totalScoreCourse
        restTotalScoreCourseMockMvc
            .perform(delete(ENTITY_API_URL_ID, totalScoreCourse.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TotalScoreCourse> totalScoreCourseList = totalScoreCourseRepository.findAll();
        assertThat(totalScoreCourseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
