package com.evaluar_cursos.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.evaluar_cursos.IntegrationTest;
import com.evaluar_cursos.domain.EvaluationProfesor;
import com.evaluar_cursos.repository.EvaluationProfesorRepository;
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
 * Integration tests for the {@link EvaluationProfesorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EvaluationProfesorResourceIT {

    private static final Integer DEFAULT_P_SCORE_QUESTION_ONE = 1;
    private static final Integer UPDATED_P_SCORE_QUESTION_ONE = 2;

    private static final Integer DEFAULT_P_SCORE_QUESTION_TWO = 1;
    private static final Integer UPDATED_P_SCORE_QUESTION_TWO = 2;

    private static final Integer DEFAULT_P_SCORE_QUESTION_THREE = 1;
    private static final Integer UPDATED_P_SCORE_QUESTION_THREE = 2;

    private static final Integer DEFAULT_P_SCORE_QUESTION_FOUR = 1;
    private static final Integer UPDATED_P_SCORE_QUESTION_FOUR = 2;

    private static final Integer DEFAULT_P_SCORE_QUESTION_FIVE = 1;
    private static final Integer UPDATED_P_SCORE_QUESTION_FIVE = 2;

    private static final Integer DEFAULT_P_SCORE_QUESTION_SIX = 1;
    private static final Integer UPDATED_P_SCORE_QUESTION_SIX = 2;

    private static final String ENTITY_API_URL = "/api/evaluation-profesors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EvaluationProfesorRepository evaluationProfesorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEvaluationProfesorMockMvc;

    private EvaluationProfesor evaluationProfesor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EvaluationProfesor createEntity(EntityManager em) {
        EvaluationProfesor evaluationProfesor = new EvaluationProfesor()
            .pScoreQuestionOne(DEFAULT_P_SCORE_QUESTION_ONE)
            .pScoreQuestionTwo(DEFAULT_P_SCORE_QUESTION_TWO)
            .pScoreQuestionThree(DEFAULT_P_SCORE_QUESTION_THREE)
            .pScoreQuestionFour(DEFAULT_P_SCORE_QUESTION_FOUR)
            .pScoreQuestionFive(DEFAULT_P_SCORE_QUESTION_FIVE)
            .pScoreQuestionSix(DEFAULT_P_SCORE_QUESTION_SIX);
        return evaluationProfesor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EvaluationProfesor createUpdatedEntity(EntityManager em) {
        EvaluationProfesor evaluationProfesor = new EvaluationProfesor()
            .pScoreQuestionOne(UPDATED_P_SCORE_QUESTION_ONE)
            .pScoreQuestionTwo(UPDATED_P_SCORE_QUESTION_TWO)
            .pScoreQuestionThree(UPDATED_P_SCORE_QUESTION_THREE)
            .pScoreQuestionFour(UPDATED_P_SCORE_QUESTION_FOUR)
            .pScoreQuestionFive(UPDATED_P_SCORE_QUESTION_FIVE)
            .pScoreQuestionSix(UPDATED_P_SCORE_QUESTION_SIX);
        return evaluationProfesor;
    }

    @BeforeEach
    public void initTest() {
        evaluationProfesor = createEntity(em);
    }

    @Test
    @Transactional
    void createEvaluationProfesor() throws Exception {
        int databaseSizeBeforeCreate = evaluationProfesorRepository.findAll().size();
        // Create the EvaluationProfesor
        restEvaluationProfesorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evaluationProfesor))
            )
            .andExpect(status().isCreated());

        // Validate the EvaluationProfesor in the database
        List<EvaluationProfesor> evaluationProfesorList = evaluationProfesorRepository.findAll();
        assertThat(evaluationProfesorList).hasSize(databaseSizeBeforeCreate + 1);
        EvaluationProfesor testEvaluationProfesor = evaluationProfesorList.get(evaluationProfesorList.size() - 1);
        assertThat(testEvaluationProfesor.getpScoreQuestionOne()).isEqualTo(DEFAULT_P_SCORE_QUESTION_ONE);
        assertThat(testEvaluationProfesor.getpScoreQuestionTwo()).isEqualTo(DEFAULT_P_SCORE_QUESTION_TWO);
        assertThat(testEvaluationProfesor.getpScoreQuestionThree()).isEqualTo(DEFAULT_P_SCORE_QUESTION_THREE);
        assertThat(testEvaluationProfesor.getpScoreQuestionFour()).isEqualTo(DEFAULT_P_SCORE_QUESTION_FOUR);
        assertThat(testEvaluationProfesor.getpScoreQuestionFive()).isEqualTo(DEFAULT_P_SCORE_QUESTION_FIVE);
        assertThat(testEvaluationProfesor.getpScoreQuestionSix()).isEqualTo(DEFAULT_P_SCORE_QUESTION_SIX);
    }

    @Test
    @Transactional
    void createEvaluationProfesorWithExistingId() throws Exception {
        // Create the EvaluationProfesor with an existing ID
        evaluationProfesor.setId(1L);

        int databaseSizeBeforeCreate = evaluationProfesorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvaluationProfesorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evaluationProfesor))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationProfesor in the database
        List<EvaluationProfesor> evaluationProfesorList = evaluationProfesorRepository.findAll();
        assertThat(evaluationProfesorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEvaluationProfesors() throws Exception {
        // Initialize the database
        evaluationProfesorRepository.saveAndFlush(evaluationProfesor);

        // Get all the evaluationProfesorList
        restEvaluationProfesorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evaluationProfesor.getId().intValue())))
            .andExpect(jsonPath("$.[*].pScoreQuestionOne").value(hasItem(DEFAULT_P_SCORE_QUESTION_ONE)))
            .andExpect(jsonPath("$.[*].pScoreQuestionTwo").value(hasItem(DEFAULT_P_SCORE_QUESTION_TWO)))
            .andExpect(jsonPath("$.[*].pScoreQuestionThree").value(hasItem(DEFAULT_P_SCORE_QUESTION_THREE)))
            .andExpect(jsonPath("$.[*].pScoreQuestionFour").value(hasItem(DEFAULT_P_SCORE_QUESTION_FOUR)))
            .andExpect(jsonPath("$.[*].pScoreQuestionFive").value(hasItem(DEFAULT_P_SCORE_QUESTION_FIVE)))
            .andExpect(jsonPath("$.[*].pScoreQuestionSix").value(hasItem(DEFAULT_P_SCORE_QUESTION_SIX)));
    }

    @Test
    @Transactional
    void getEvaluationProfesor() throws Exception {
        // Initialize the database
        evaluationProfesorRepository.saveAndFlush(evaluationProfesor);

        // Get the evaluationProfesor
        restEvaluationProfesorMockMvc
            .perform(get(ENTITY_API_URL_ID, evaluationProfesor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(evaluationProfesor.getId().intValue()))
            .andExpect(jsonPath("$.pScoreQuestionOne").value(DEFAULT_P_SCORE_QUESTION_ONE))
            .andExpect(jsonPath("$.pScoreQuestionTwo").value(DEFAULT_P_SCORE_QUESTION_TWO))
            .andExpect(jsonPath("$.pScoreQuestionThree").value(DEFAULT_P_SCORE_QUESTION_THREE))
            .andExpect(jsonPath("$.pScoreQuestionFour").value(DEFAULT_P_SCORE_QUESTION_FOUR))
            .andExpect(jsonPath("$.pScoreQuestionFive").value(DEFAULT_P_SCORE_QUESTION_FIVE))
            .andExpect(jsonPath("$.pScoreQuestionSix").value(DEFAULT_P_SCORE_QUESTION_SIX));
    }

    @Test
    @Transactional
    void getNonExistingEvaluationProfesor() throws Exception {
        // Get the evaluationProfesor
        restEvaluationProfesorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEvaluationProfesor() throws Exception {
        // Initialize the database
        evaluationProfesorRepository.saveAndFlush(evaluationProfesor);

        int databaseSizeBeforeUpdate = evaluationProfesorRepository.findAll().size();

        // Update the evaluationProfesor
        EvaluationProfesor updatedEvaluationProfesor = evaluationProfesorRepository.findById(evaluationProfesor.getId()).get();
        // Disconnect from session so that the updates on updatedEvaluationProfesor are not directly saved in db
        em.detach(updatedEvaluationProfesor);
        updatedEvaluationProfesor
            .pScoreQuestionOne(UPDATED_P_SCORE_QUESTION_ONE)
            .pScoreQuestionTwo(UPDATED_P_SCORE_QUESTION_TWO)
            .pScoreQuestionThree(UPDATED_P_SCORE_QUESTION_THREE)
            .pScoreQuestionFour(UPDATED_P_SCORE_QUESTION_FOUR)
            .pScoreQuestionFive(UPDATED_P_SCORE_QUESTION_FIVE)
            .pScoreQuestionSix(UPDATED_P_SCORE_QUESTION_SIX);

        restEvaluationProfesorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEvaluationProfesor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEvaluationProfesor))
            )
            .andExpect(status().isOk());

        // Validate the EvaluationProfesor in the database
        List<EvaluationProfesor> evaluationProfesorList = evaluationProfesorRepository.findAll();
        assertThat(evaluationProfesorList).hasSize(databaseSizeBeforeUpdate);
        EvaluationProfesor testEvaluationProfesor = evaluationProfesorList.get(evaluationProfesorList.size() - 1);
        assertThat(testEvaluationProfesor.getpScoreQuestionOne()).isEqualTo(UPDATED_P_SCORE_QUESTION_ONE);
        assertThat(testEvaluationProfesor.getpScoreQuestionTwo()).isEqualTo(UPDATED_P_SCORE_QUESTION_TWO);
        assertThat(testEvaluationProfesor.getpScoreQuestionThree()).isEqualTo(UPDATED_P_SCORE_QUESTION_THREE);
        assertThat(testEvaluationProfesor.getpScoreQuestionFour()).isEqualTo(UPDATED_P_SCORE_QUESTION_FOUR);
        assertThat(testEvaluationProfesor.getpScoreQuestionFive()).isEqualTo(UPDATED_P_SCORE_QUESTION_FIVE);
        assertThat(testEvaluationProfesor.getpScoreQuestionSix()).isEqualTo(UPDATED_P_SCORE_QUESTION_SIX);
    }

    @Test
    @Transactional
    void putNonExistingEvaluationProfesor() throws Exception {
        int databaseSizeBeforeUpdate = evaluationProfesorRepository.findAll().size();
        evaluationProfesor.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvaluationProfesorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, evaluationProfesor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationProfesor))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationProfesor in the database
        List<EvaluationProfesor> evaluationProfesorList = evaluationProfesorRepository.findAll();
        assertThat(evaluationProfesorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEvaluationProfesor() throws Exception {
        int databaseSizeBeforeUpdate = evaluationProfesorRepository.findAll().size();
        evaluationProfesor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluationProfesorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationProfesor))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationProfesor in the database
        List<EvaluationProfesor> evaluationProfesorList = evaluationProfesorRepository.findAll();
        assertThat(evaluationProfesorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEvaluationProfesor() throws Exception {
        int databaseSizeBeforeUpdate = evaluationProfesorRepository.findAll().size();
        evaluationProfesor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluationProfesorMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evaluationProfesor))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EvaluationProfesor in the database
        List<EvaluationProfesor> evaluationProfesorList = evaluationProfesorRepository.findAll();
        assertThat(evaluationProfesorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEvaluationProfesorWithPatch() throws Exception {
        // Initialize the database
        evaluationProfesorRepository.saveAndFlush(evaluationProfesor);

        int databaseSizeBeforeUpdate = evaluationProfesorRepository.findAll().size();

        // Update the evaluationProfesor using partial update
        EvaluationProfesor partialUpdatedEvaluationProfesor = new EvaluationProfesor();
        partialUpdatedEvaluationProfesor.setId(evaluationProfesor.getId());

        partialUpdatedEvaluationProfesor
            .pScoreQuestionTwo(UPDATED_P_SCORE_QUESTION_TWO)
            .pScoreQuestionThree(UPDATED_P_SCORE_QUESTION_THREE);

        restEvaluationProfesorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvaluationProfesor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvaluationProfesor))
            )
            .andExpect(status().isOk());

        // Validate the EvaluationProfesor in the database
        List<EvaluationProfesor> evaluationProfesorList = evaluationProfesorRepository.findAll();
        assertThat(evaluationProfesorList).hasSize(databaseSizeBeforeUpdate);
        EvaluationProfesor testEvaluationProfesor = evaluationProfesorList.get(evaluationProfesorList.size() - 1);
        assertThat(testEvaluationProfesor.getpScoreQuestionOne()).isEqualTo(DEFAULT_P_SCORE_QUESTION_ONE);
        assertThat(testEvaluationProfesor.getpScoreQuestionTwo()).isEqualTo(UPDATED_P_SCORE_QUESTION_TWO);
        assertThat(testEvaluationProfesor.getpScoreQuestionThree()).isEqualTo(UPDATED_P_SCORE_QUESTION_THREE);
        assertThat(testEvaluationProfesor.getpScoreQuestionFour()).isEqualTo(DEFAULT_P_SCORE_QUESTION_FOUR);
        assertThat(testEvaluationProfesor.getpScoreQuestionFive()).isEqualTo(DEFAULT_P_SCORE_QUESTION_FIVE);
        assertThat(testEvaluationProfesor.getpScoreQuestionSix()).isEqualTo(DEFAULT_P_SCORE_QUESTION_SIX);
    }

    @Test
    @Transactional
    void fullUpdateEvaluationProfesorWithPatch() throws Exception {
        // Initialize the database
        evaluationProfesorRepository.saveAndFlush(evaluationProfesor);

        int databaseSizeBeforeUpdate = evaluationProfesorRepository.findAll().size();

        // Update the evaluationProfesor using partial update
        EvaluationProfesor partialUpdatedEvaluationProfesor = new EvaluationProfesor();
        partialUpdatedEvaluationProfesor.setId(evaluationProfesor.getId());

        partialUpdatedEvaluationProfesor
            .pScoreQuestionOne(UPDATED_P_SCORE_QUESTION_ONE)
            .pScoreQuestionTwo(UPDATED_P_SCORE_QUESTION_TWO)
            .pScoreQuestionThree(UPDATED_P_SCORE_QUESTION_THREE)
            .pScoreQuestionFour(UPDATED_P_SCORE_QUESTION_FOUR)
            .pScoreQuestionFive(UPDATED_P_SCORE_QUESTION_FIVE)
            .pScoreQuestionSix(UPDATED_P_SCORE_QUESTION_SIX);

        restEvaluationProfesorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvaluationProfesor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvaluationProfesor))
            )
            .andExpect(status().isOk());

        // Validate the EvaluationProfesor in the database
        List<EvaluationProfesor> evaluationProfesorList = evaluationProfesorRepository.findAll();
        assertThat(evaluationProfesorList).hasSize(databaseSizeBeforeUpdate);
        EvaluationProfesor testEvaluationProfesor = evaluationProfesorList.get(evaluationProfesorList.size() - 1);
        assertThat(testEvaluationProfesor.getpScoreQuestionOne()).isEqualTo(UPDATED_P_SCORE_QUESTION_ONE);
        assertThat(testEvaluationProfesor.getpScoreQuestionTwo()).isEqualTo(UPDATED_P_SCORE_QUESTION_TWO);
        assertThat(testEvaluationProfesor.getpScoreQuestionThree()).isEqualTo(UPDATED_P_SCORE_QUESTION_THREE);
        assertThat(testEvaluationProfesor.getpScoreQuestionFour()).isEqualTo(UPDATED_P_SCORE_QUESTION_FOUR);
        assertThat(testEvaluationProfesor.getpScoreQuestionFive()).isEqualTo(UPDATED_P_SCORE_QUESTION_FIVE);
        assertThat(testEvaluationProfesor.getpScoreQuestionSix()).isEqualTo(UPDATED_P_SCORE_QUESTION_SIX);
    }

    @Test
    @Transactional
    void patchNonExistingEvaluationProfesor() throws Exception {
        int databaseSizeBeforeUpdate = evaluationProfesorRepository.findAll().size();
        evaluationProfesor.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvaluationProfesorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, evaluationProfesor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evaluationProfesor))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationProfesor in the database
        List<EvaluationProfesor> evaluationProfesorList = evaluationProfesorRepository.findAll();
        assertThat(evaluationProfesorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEvaluationProfesor() throws Exception {
        int databaseSizeBeforeUpdate = evaluationProfesorRepository.findAll().size();
        evaluationProfesor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluationProfesorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evaluationProfesor))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationProfesor in the database
        List<EvaluationProfesor> evaluationProfesorList = evaluationProfesorRepository.findAll();
        assertThat(evaluationProfesorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEvaluationProfesor() throws Exception {
        int databaseSizeBeforeUpdate = evaluationProfesorRepository.findAll().size();
        evaluationProfesor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluationProfesorMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evaluationProfesor))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EvaluationProfesor in the database
        List<EvaluationProfesor> evaluationProfesorList = evaluationProfesorRepository.findAll();
        assertThat(evaluationProfesorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEvaluationProfesor() throws Exception {
        // Initialize the database
        evaluationProfesorRepository.saveAndFlush(evaluationProfesor);

        int databaseSizeBeforeDelete = evaluationProfesorRepository.findAll().size();

        // Delete the evaluationProfesor
        restEvaluationProfesorMockMvc
            .perform(delete(ENTITY_API_URL_ID, evaluationProfesor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EvaluationProfesor> evaluationProfesorList = evaluationProfesorRepository.findAll();
        assertThat(evaluationProfesorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
