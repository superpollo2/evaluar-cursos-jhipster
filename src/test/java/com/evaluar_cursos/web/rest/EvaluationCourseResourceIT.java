package com.evaluar_cursos.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.evaluar_cursos.IntegrationTest;
import com.evaluar_cursos.domain.EvaluationCourse;
import com.evaluar_cursos.repository.EvaluationCourseRepository;
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
 * Integration tests for the {@link EvaluationCourseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EvaluationCourseResourceIT {

    private static final Integer DEFAULT_C_SCORE_QUESTION_ONE = 1;
    private static final Integer UPDATED_C_SCORE_QUESTION_ONE = 2;

    private static final Integer DEFAULT_C_SCORE_QUESTION_TWO = 1;
    private static final Integer UPDATED_C_SCORE_QUESTION_TWO = 2;

    private static final Integer DEFAULT_C_SCORE_QUESTION_THREE = 1;
    private static final Integer UPDATED_C_SCORE_QUESTION_THREE = 2;

    private static final Integer DEFAULT_C_SCORE_QUESTION_FOUR = 1;
    private static final Integer UPDATED_C_SCORE_QUESTION_FOUR = 2;

    private static final Integer DEFAULT_C_SCORE_QUESTION_FIVE = 1;
    private static final Integer UPDATED_C_SCORE_QUESTION_FIVE = 2;

    private static final Integer DEFAULT_C_SCORE_QUESTION_SIX = 1;
    private static final Integer UPDATED_C_SCORE_QUESTION_SIX = 2;

    private static final String ENTITY_API_URL = "/api/evaluation-courses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EvaluationCourseRepository evaluationCourseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEvaluationCourseMockMvc;

    private EvaluationCourse evaluationCourse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EvaluationCourse createEntity(EntityManager em) {
        EvaluationCourse evaluationCourse = new EvaluationCourse()
            .cScoreQuestionOne(DEFAULT_C_SCORE_QUESTION_ONE)
            .cScoreQuestionTwo(DEFAULT_C_SCORE_QUESTION_TWO)
            .cScoreQuestionThree(DEFAULT_C_SCORE_QUESTION_THREE)
            .cScoreQuestionFour(DEFAULT_C_SCORE_QUESTION_FOUR)
            .cScoreQuestionFive(DEFAULT_C_SCORE_QUESTION_FIVE)
            .cScoreQuestionSix(DEFAULT_C_SCORE_QUESTION_SIX);
        return evaluationCourse;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EvaluationCourse createUpdatedEntity(EntityManager em) {
        EvaluationCourse evaluationCourse = new EvaluationCourse()
            .cScoreQuestionOne(UPDATED_C_SCORE_QUESTION_ONE)
            .cScoreQuestionTwo(UPDATED_C_SCORE_QUESTION_TWO)
            .cScoreQuestionThree(UPDATED_C_SCORE_QUESTION_THREE)
            .cScoreQuestionFour(UPDATED_C_SCORE_QUESTION_FOUR)
            .cScoreQuestionFive(UPDATED_C_SCORE_QUESTION_FIVE)
            .cScoreQuestionSix(UPDATED_C_SCORE_QUESTION_SIX);
        return evaluationCourse;
    }

    @BeforeEach
    public void initTest() {
        evaluationCourse = createEntity(em);
    }

    @Test
    @Transactional
    void createEvaluationCourse() throws Exception {
        int databaseSizeBeforeCreate = evaluationCourseRepository.findAll().size();
        // Create the EvaluationCourse
        restEvaluationCourseMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evaluationCourse))
            )
            .andExpect(status().isCreated());

        // Validate the EvaluationCourse in the database
        List<EvaluationCourse> evaluationCourseList = evaluationCourseRepository.findAll();
        assertThat(evaluationCourseList).hasSize(databaseSizeBeforeCreate + 1);
        EvaluationCourse testEvaluationCourse = evaluationCourseList.get(evaluationCourseList.size() - 1);
        assertThat(testEvaluationCourse.getcScoreQuestionOne()).isEqualTo(DEFAULT_C_SCORE_QUESTION_ONE);
        assertThat(testEvaluationCourse.getcScoreQuestionTwo()).isEqualTo(DEFAULT_C_SCORE_QUESTION_TWO);
        assertThat(testEvaluationCourse.getcScoreQuestionThree()).isEqualTo(DEFAULT_C_SCORE_QUESTION_THREE);
        assertThat(testEvaluationCourse.getcScoreQuestionFour()).isEqualTo(DEFAULT_C_SCORE_QUESTION_FOUR);
        assertThat(testEvaluationCourse.getcScoreQuestionFive()).isEqualTo(DEFAULT_C_SCORE_QUESTION_FIVE);
        assertThat(testEvaluationCourse.getcScoreQuestionSix()).isEqualTo(DEFAULT_C_SCORE_QUESTION_SIX);
    }

    @Test
    @Transactional
    void createEvaluationCourseWithExistingId() throws Exception {
        // Create the EvaluationCourse with an existing ID
        evaluationCourse.setId(1L);

        int databaseSizeBeforeCreate = evaluationCourseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvaluationCourseMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evaluationCourse))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationCourse in the database
        List<EvaluationCourse> evaluationCourseList = evaluationCourseRepository.findAll();
        assertThat(evaluationCourseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEvaluationCourses() throws Exception {
        // Initialize the database
        evaluationCourseRepository.saveAndFlush(evaluationCourse);

        // Get all the evaluationCourseList
        restEvaluationCourseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evaluationCourse.getId().intValue())))
            .andExpect(jsonPath("$.[*].cScoreQuestionOne").value(hasItem(DEFAULT_C_SCORE_QUESTION_ONE)))
            .andExpect(jsonPath("$.[*].cScoreQuestionTwo").value(hasItem(DEFAULT_C_SCORE_QUESTION_TWO)))
            .andExpect(jsonPath("$.[*].cScoreQuestionThree").value(hasItem(DEFAULT_C_SCORE_QUESTION_THREE)))
            .andExpect(jsonPath("$.[*].cScoreQuestionFour").value(hasItem(DEFAULT_C_SCORE_QUESTION_FOUR)))
            .andExpect(jsonPath("$.[*].cScoreQuestionFive").value(hasItem(DEFAULT_C_SCORE_QUESTION_FIVE)))
            .andExpect(jsonPath("$.[*].cScoreQuestionSix").value(hasItem(DEFAULT_C_SCORE_QUESTION_SIX)));
    }

    @Test
    @Transactional
    void getEvaluationCourse() throws Exception {
        // Initialize the database
        evaluationCourseRepository.saveAndFlush(evaluationCourse);

        // Get the evaluationCourse
        restEvaluationCourseMockMvc
            .perform(get(ENTITY_API_URL_ID, evaluationCourse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(evaluationCourse.getId().intValue()))
            .andExpect(jsonPath("$.cScoreQuestionOne").value(DEFAULT_C_SCORE_QUESTION_ONE))
            .andExpect(jsonPath("$.cScoreQuestionTwo").value(DEFAULT_C_SCORE_QUESTION_TWO))
            .andExpect(jsonPath("$.cScoreQuestionThree").value(DEFAULT_C_SCORE_QUESTION_THREE))
            .andExpect(jsonPath("$.cScoreQuestionFour").value(DEFAULT_C_SCORE_QUESTION_FOUR))
            .andExpect(jsonPath("$.cScoreQuestionFive").value(DEFAULT_C_SCORE_QUESTION_FIVE))
            .andExpect(jsonPath("$.cScoreQuestionSix").value(DEFAULT_C_SCORE_QUESTION_SIX));
    }

    @Test
    @Transactional
    void getNonExistingEvaluationCourse() throws Exception {
        // Get the evaluationCourse
        restEvaluationCourseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEvaluationCourse() throws Exception {
        // Initialize the database
        evaluationCourseRepository.saveAndFlush(evaluationCourse);

        int databaseSizeBeforeUpdate = evaluationCourseRepository.findAll().size();

        // Update the evaluationCourse
        EvaluationCourse updatedEvaluationCourse = evaluationCourseRepository.findById(evaluationCourse.getId()).get();
        // Disconnect from session so that the updates on updatedEvaluationCourse are not directly saved in db
        em.detach(updatedEvaluationCourse);
        updatedEvaluationCourse
            .cScoreQuestionOne(UPDATED_C_SCORE_QUESTION_ONE)
            .cScoreQuestionTwo(UPDATED_C_SCORE_QUESTION_TWO)
            .cScoreQuestionThree(UPDATED_C_SCORE_QUESTION_THREE)
            .cScoreQuestionFour(UPDATED_C_SCORE_QUESTION_FOUR)
            .cScoreQuestionFive(UPDATED_C_SCORE_QUESTION_FIVE)
            .cScoreQuestionSix(UPDATED_C_SCORE_QUESTION_SIX);

        restEvaluationCourseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEvaluationCourse.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEvaluationCourse))
            )
            .andExpect(status().isOk());

        // Validate the EvaluationCourse in the database
        List<EvaluationCourse> evaluationCourseList = evaluationCourseRepository.findAll();
        assertThat(evaluationCourseList).hasSize(databaseSizeBeforeUpdate);
        EvaluationCourse testEvaluationCourse = evaluationCourseList.get(evaluationCourseList.size() - 1);
        assertThat(testEvaluationCourse.getcScoreQuestionOne()).isEqualTo(UPDATED_C_SCORE_QUESTION_ONE);
        assertThat(testEvaluationCourse.getcScoreQuestionTwo()).isEqualTo(UPDATED_C_SCORE_QUESTION_TWO);
        assertThat(testEvaluationCourse.getcScoreQuestionThree()).isEqualTo(UPDATED_C_SCORE_QUESTION_THREE);
        assertThat(testEvaluationCourse.getcScoreQuestionFour()).isEqualTo(UPDATED_C_SCORE_QUESTION_FOUR);
        assertThat(testEvaluationCourse.getcScoreQuestionFive()).isEqualTo(UPDATED_C_SCORE_QUESTION_FIVE);
        assertThat(testEvaluationCourse.getcScoreQuestionSix()).isEqualTo(UPDATED_C_SCORE_QUESTION_SIX);
    }

    @Test
    @Transactional
    void putNonExistingEvaluationCourse() throws Exception {
        int databaseSizeBeforeUpdate = evaluationCourseRepository.findAll().size();
        evaluationCourse.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvaluationCourseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, evaluationCourse.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationCourse))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationCourse in the database
        List<EvaluationCourse> evaluationCourseList = evaluationCourseRepository.findAll();
        assertThat(evaluationCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEvaluationCourse() throws Exception {
        int databaseSizeBeforeUpdate = evaluationCourseRepository.findAll().size();
        evaluationCourse.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluationCourseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationCourse))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationCourse in the database
        List<EvaluationCourse> evaluationCourseList = evaluationCourseRepository.findAll();
        assertThat(evaluationCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEvaluationCourse() throws Exception {
        int databaseSizeBeforeUpdate = evaluationCourseRepository.findAll().size();
        evaluationCourse.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluationCourseMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evaluationCourse))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EvaluationCourse in the database
        List<EvaluationCourse> evaluationCourseList = evaluationCourseRepository.findAll();
        assertThat(evaluationCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEvaluationCourseWithPatch() throws Exception {
        // Initialize the database
        evaluationCourseRepository.saveAndFlush(evaluationCourse);

        int databaseSizeBeforeUpdate = evaluationCourseRepository.findAll().size();

        // Update the evaluationCourse using partial update
        EvaluationCourse partialUpdatedEvaluationCourse = new EvaluationCourse();
        partialUpdatedEvaluationCourse.setId(evaluationCourse.getId());

        partialUpdatedEvaluationCourse.cScoreQuestionOne(UPDATED_C_SCORE_QUESTION_ONE).cScoreQuestionThree(UPDATED_C_SCORE_QUESTION_THREE);

        restEvaluationCourseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvaluationCourse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvaluationCourse))
            )
            .andExpect(status().isOk());

        // Validate the EvaluationCourse in the database
        List<EvaluationCourse> evaluationCourseList = evaluationCourseRepository.findAll();
        assertThat(evaluationCourseList).hasSize(databaseSizeBeforeUpdate);
        EvaluationCourse testEvaluationCourse = evaluationCourseList.get(evaluationCourseList.size() - 1);
        assertThat(testEvaluationCourse.getcScoreQuestionOne()).isEqualTo(UPDATED_C_SCORE_QUESTION_ONE);
        assertThat(testEvaluationCourse.getcScoreQuestionTwo()).isEqualTo(DEFAULT_C_SCORE_QUESTION_TWO);
        assertThat(testEvaluationCourse.getcScoreQuestionThree()).isEqualTo(UPDATED_C_SCORE_QUESTION_THREE);
        assertThat(testEvaluationCourse.getcScoreQuestionFour()).isEqualTo(DEFAULT_C_SCORE_QUESTION_FOUR);
        assertThat(testEvaluationCourse.getcScoreQuestionFive()).isEqualTo(DEFAULT_C_SCORE_QUESTION_FIVE);
        assertThat(testEvaluationCourse.getcScoreQuestionSix()).isEqualTo(DEFAULT_C_SCORE_QUESTION_SIX);
    }

    @Test
    @Transactional
    void fullUpdateEvaluationCourseWithPatch() throws Exception {
        // Initialize the database
        evaluationCourseRepository.saveAndFlush(evaluationCourse);

        int databaseSizeBeforeUpdate = evaluationCourseRepository.findAll().size();

        // Update the evaluationCourse using partial update
        EvaluationCourse partialUpdatedEvaluationCourse = new EvaluationCourse();
        partialUpdatedEvaluationCourse.setId(evaluationCourse.getId());

        partialUpdatedEvaluationCourse
            .cScoreQuestionOne(UPDATED_C_SCORE_QUESTION_ONE)
            .cScoreQuestionTwo(UPDATED_C_SCORE_QUESTION_TWO)
            .cScoreQuestionThree(UPDATED_C_SCORE_QUESTION_THREE)
            .cScoreQuestionFour(UPDATED_C_SCORE_QUESTION_FOUR)
            .cScoreQuestionFive(UPDATED_C_SCORE_QUESTION_FIVE)
            .cScoreQuestionSix(UPDATED_C_SCORE_QUESTION_SIX);

        restEvaluationCourseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvaluationCourse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvaluationCourse))
            )
            .andExpect(status().isOk());

        // Validate the EvaluationCourse in the database
        List<EvaluationCourse> evaluationCourseList = evaluationCourseRepository.findAll();
        assertThat(evaluationCourseList).hasSize(databaseSizeBeforeUpdate);
        EvaluationCourse testEvaluationCourse = evaluationCourseList.get(evaluationCourseList.size() - 1);
        assertThat(testEvaluationCourse.getcScoreQuestionOne()).isEqualTo(UPDATED_C_SCORE_QUESTION_ONE);
        assertThat(testEvaluationCourse.getcScoreQuestionTwo()).isEqualTo(UPDATED_C_SCORE_QUESTION_TWO);
        assertThat(testEvaluationCourse.getcScoreQuestionThree()).isEqualTo(UPDATED_C_SCORE_QUESTION_THREE);
        assertThat(testEvaluationCourse.getcScoreQuestionFour()).isEqualTo(UPDATED_C_SCORE_QUESTION_FOUR);
        assertThat(testEvaluationCourse.getcScoreQuestionFive()).isEqualTo(UPDATED_C_SCORE_QUESTION_FIVE);
        assertThat(testEvaluationCourse.getcScoreQuestionSix()).isEqualTo(UPDATED_C_SCORE_QUESTION_SIX);
    }

    @Test
    @Transactional
    void patchNonExistingEvaluationCourse() throws Exception {
        int databaseSizeBeforeUpdate = evaluationCourseRepository.findAll().size();
        evaluationCourse.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvaluationCourseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, evaluationCourse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evaluationCourse))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationCourse in the database
        List<EvaluationCourse> evaluationCourseList = evaluationCourseRepository.findAll();
        assertThat(evaluationCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEvaluationCourse() throws Exception {
        int databaseSizeBeforeUpdate = evaluationCourseRepository.findAll().size();
        evaluationCourse.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluationCourseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evaluationCourse))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationCourse in the database
        List<EvaluationCourse> evaluationCourseList = evaluationCourseRepository.findAll();
        assertThat(evaluationCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEvaluationCourse() throws Exception {
        int databaseSizeBeforeUpdate = evaluationCourseRepository.findAll().size();
        evaluationCourse.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluationCourseMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evaluationCourse))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EvaluationCourse in the database
        List<EvaluationCourse> evaluationCourseList = evaluationCourseRepository.findAll();
        assertThat(evaluationCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEvaluationCourse() throws Exception {
        // Initialize the database
        evaluationCourseRepository.saveAndFlush(evaluationCourse);

        int databaseSizeBeforeDelete = evaluationCourseRepository.findAll().size();

        // Delete the evaluationCourse
        restEvaluationCourseMockMvc
            .perform(delete(ENTITY_API_URL_ID, evaluationCourse.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EvaluationCourse> evaluationCourseList = evaluationCourseRepository.findAll();
        assertThat(evaluationCourseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
