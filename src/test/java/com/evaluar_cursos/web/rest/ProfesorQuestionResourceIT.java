package com.evaluar_cursos.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.evaluar_cursos.IntegrationTest;
import com.evaluar_cursos.domain.ProfesorQuestion;
import com.evaluar_cursos.repository.ProfesorQuestionRepository;
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
 * Integration tests for the {@link ProfesorQuestionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProfesorQuestionResourceIT {

    private static final UUID DEFAULT_QUESTION_ID = UUID.randomUUID();
    private static final UUID UPDATED_QUESTION_ID = UUID.randomUUID();

    private static final String DEFAULT_PROFESOR_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_PROFESOR_QUESTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/profesor-questions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProfesorQuestionRepository profesorQuestionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfesorQuestionMockMvc;

    private ProfesorQuestion profesorQuestion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProfesorQuestion createEntity(EntityManager em) {
        ProfesorQuestion profesorQuestion = new ProfesorQuestion()
            .questionId(DEFAULT_QUESTION_ID)
            .profesorQuestion(DEFAULT_PROFESOR_QUESTION);
        return profesorQuestion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProfesorQuestion createUpdatedEntity(EntityManager em) {
        ProfesorQuestion profesorQuestion = new ProfesorQuestion()
            .questionId(UPDATED_QUESTION_ID)
            .profesorQuestion(UPDATED_PROFESOR_QUESTION);
        return profesorQuestion;
    }

    @BeforeEach
    public void initTest() {
        profesorQuestion = createEntity(em);
    }

    @Test
    @Transactional
    void createProfesorQuestion() throws Exception {
        int databaseSizeBeforeCreate = profesorQuestionRepository.findAll().size();
        // Create the ProfesorQuestion
        restProfesorQuestionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profesorQuestion))
            )
            .andExpect(status().isCreated());

        // Validate the ProfesorQuestion in the database
        List<ProfesorQuestion> profesorQuestionList = profesorQuestionRepository.findAll();
        assertThat(profesorQuestionList).hasSize(databaseSizeBeforeCreate + 1);
        ProfesorQuestion testProfesorQuestion = profesorQuestionList.get(profesorQuestionList.size() - 1);
        assertThat(testProfesorQuestion.getQuestionId()).isEqualTo(DEFAULT_QUESTION_ID);
        assertThat(testProfesorQuestion.getProfesorQuestion()).isEqualTo(DEFAULT_PROFESOR_QUESTION);
    }

    @Test
    @Transactional
    void createProfesorQuestionWithExistingId() throws Exception {
        // Create the ProfesorQuestion with an existing ID
        profesorQuestion.setId(1L);

        int databaseSizeBeforeCreate = profesorQuestionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfesorQuestionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profesorQuestion))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProfesorQuestion in the database
        List<ProfesorQuestion> profesorQuestionList = profesorQuestionRepository.findAll();
        assertThat(profesorQuestionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProfesorQuestions() throws Exception {
        // Initialize the database
        profesorQuestionRepository.saveAndFlush(profesorQuestion);

        // Get all the profesorQuestionList
        restProfesorQuestionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profesorQuestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].questionId").value(hasItem(DEFAULT_QUESTION_ID.toString())))
            .andExpect(jsonPath("$.[*].profesorQuestion").value(hasItem(DEFAULT_PROFESOR_QUESTION)));
    }

    @Test
    @Transactional
    void getProfesorQuestion() throws Exception {
        // Initialize the database
        profesorQuestionRepository.saveAndFlush(profesorQuestion);

        // Get the profesorQuestion
        restProfesorQuestionMockMvc
            .perform(get(ENTITY_API_URL_ID, profesorQuestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(profesorQuestion.getId().intValue()))
            .andExpect(jsonPath("$.questionId").value(DEFAULT_QUESTION_ID.toString()))
            .andExpect(jsonPath("$.profesorQuestion").value(DEFAULT_PROFESOR_QUESTION));
    }

    @Test
    @Transactional
    void getNonExistingProfesorQuestion() throws Exception {
        // Get the profesorQuestion
        restProfesorQuestionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProfesorQuestion() throws Exception {
        // Initialize the database
        profesorQuestionRepository.saveAndFlush(profesorQuestion);

        int databaseSizeBeforeUpdate = profesorQuestionRepository.findAll().size();

        // Update the profesorQuestion
        ProfesorQuestion updatedProfesorQuestion = profesorQuestionRepository.findById(profesorQuestion.getId()).get();
        // Disconnect from session so that the updates on updatedProfesorQuestion are not directly saved in db
        em.detach(updatedProfesorQuestion);
        updatedProfesorQuestion.questionId(UPDATED_QUESTION_ID).profesorQuestion(UPDATED_PROFESOR_QUESTION);

        restProfesorQuestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProfesorQuestion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProfesorQuestion))
            )
            .andExpect(status().isOk());

        // Validate the ProfesorQuestion in the database
        List<ProfesorQuestion> profesorQuestionList = profesorQuestionRepository.findAll();
        assertThat(profesorQuestionList).hasSize(databaseSizeBeforeUpdate);
        ProfesorQuestion testProfesorQuestion = profesorQuestionList.get(profesorQuestionList.size() - 1);
        assertThat(testProfesorQuestion.getQuestionId()).isEqualTo(UPDATED_QUESTION_ID);
        assertThat(testProfesorQuestion.getProfesorQuestion()).isEqualTo(UPDATED_PROFESOR_QUESTION);
    }

    @Test
    @Transactional
    void putNonExistingProfesorQuestion() throws Exception {
        int databaseSizeBeforeUpdate = profesorQuestionRepository.findAll().size();
        profesorQuestion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfesorQuestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, profesorQuestion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profesorQuestion))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProfesorQuestion in the database
        List<ProfesorQuestion> profesorQuestionList = profesorQuestionRepository.findAll();
        assertThat(profesorQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProfesorQuestion() throws Exception {
        int databaseSizeBeforeUpdate = profesorQuestionRepository.findAll().size();
        profesorQuestion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfesorQuestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profesorQuestion))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProfesorQuestion in the database
        List<ProfesorQuestion> profesorQuestionList = profesorQuestionRepository.findAll();
        assertThat(profesorQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProfesorQuestion() throws Exception {
        int databaseSizeBeforeUpdate = profesorQuestionRepository.findAll().size();
        profesorQuestion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfesorQuestionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profesorQuestion))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProfesorQuestion in the database
        List<ProfesorQuestion> profesorQuestionList = profesorQuestionRepository.findAll();
        assertThat(profesorQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProfesorQuestionWithPatch() throws Exception {
        // Initialize the database
        profesorQuestionRepository.saveAndFlush(profesorQuestion);

        int databaseSizeBeforeUpdate = profesorQuestionRepository.findAll().size();

        // Update the profesorQuestion using partial update
        ProfesorQuestion partialUpdatedProfesorQuestion = new ProfesorQuestion();
        partialUpdatedProfesorQuestion.setId(profesorQuestion.getId());

        restProfesorQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfesorQuestion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProfesorQuestion))
            )
            .andExpect(status().isOk());

        // Validate the ProfesorQuestion in the database
        List<ProfesorQuestion> profesorQuestionList = profesorQuestionRepository.findAll();
        assertThat(profesorQuestionList).hasSize(databaseSizeBeforeUpdate);
        ProfesorQuestion testProfesorQuestion = profesorQuestionList.get(profesorQuestionList.size() - 1);
        assertThat(testProfesorQuestion.getQuestionId()).isEqualTo(DEFAULT_QUESTION_ID);
        assertThat(testProfesorQuestion.getProfesorQuestion()).isEqualTo(DEFAULT_PROFESOR_QUESTION);
    }

    @Test
    @Transactional
    void fullUpdateProfesorQuestionWithPatch() throws Exception {
        // Initialize the database
        profesorQuestionRepository.saveAndFlush(profesorQuestion);

        int databaseSizeBeforeUpdate = profesorQuestionRepository.findAll().size();

        // Update the profesorQuestion using partial update
        ProfesorQuestion partialUpdatedProfesorQuestion = new ProfesorQuestion();
        partialUpdatedProfesorQuestion.setId(profesorQuestion.getId());

        partialUpdatedProfesorQuestion.questionId(UPDATED_QUESTION_ID).profesorQuestion(UPDATED_PROFESOR_QUESTION);

        restProfesorQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfesorQuestion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProfesorQuestion))
            )
            .andExpect(status().isOk());

        // Validate the ProfesorQuestion in the database
        List<ProfesorQuestion> profesorQuestionList = profesorQuestionRepository.findAll();
        assertThat(profesorQuestionList).hasSize(databaseSizeBeforeUpdate);
        ProfesorQuestion testProfesorQuestion = profesorQuestionList.get(profesorQuestionList.size() - 1);
        assertThat(testProfesorQuestion.getQuestionId()).isEqualTo(UPDATED_QUESTION_ID);
        assertThat(testProfesorQuestion.getProfesorQuestion()).isEqualTo(UPDATED_PROFESOR_QUESTION);
    }

    @Test
    @Transactional
    void patchNonExistingProfesorQuestion() throws Exception {
        int databaseSizeBeforeUpdate = profesorQuestionRepository.findAll().size();
        profesorQuestion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfesorQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, profesorQuestion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(profesorQuestion))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProfesorQuestion in the database
        List<ProfesorQuestion> profesorQuestionList = profesorQuestionRepository.findAll();
        assertThat(profesorQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProfesorQuestion() throws Exception {
        int databaseSizeBeforeUpdate = profesorQuestionRepository.findAll().size();
        profesorQuestion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfesorQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(profesorQuestion))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProfesorQuestion in the database
        List<ProfesorQuestion> profesorQuestionList = profesorQuestionRepository.findAll();
        assertThat(profesorQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProfesorQuestion() throws Exception {
        int databaseSizeBeforeUpdate = profesorQuestionRepository.findAll().size();
        profesorQuestion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfesorQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(profesorQuestion))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProfesorQuestion in the database
        List<ProfesorQuestion> profesorQuestionList = profesorQuestionRepository.findAll();
        assertThat(profesorQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProfesorQuestion() throws Exception {
        // Initialize the database
        profesorQuestionRepository.saveAndFlush(profesorQuestion);

        int databaseSizeBeforeDelete = profesorQuestionRepository.findAll().size();

        // Delete the profesorQuestion
        restProfesorQuestionMockMvc
            .perform(delete(ENTITY_API_URL_ID, profesorQuestion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProfesorQuestion> profesorQuestionList = profesorQuestionRepository.findAll();
        assertThat(profesorQuestionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
