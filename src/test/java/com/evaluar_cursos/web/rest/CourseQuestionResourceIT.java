package com.evaluar_cursos.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.evaluar_cursos.IntegrationTest;
import com.evaluar_cursos.domain.CourseQuestion;
import com.evaluar_cursos.repository.CourseQuestionRepository;
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
 * Integration tests for the {@link CourseQuestionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CourseQuestionResourceIT {

    private static final UUID DEFAULT_QUESTION_ID = UUID.randomUUID();
    private static final UUID UPDATED_QUESTION_ID = UUID.randomUUID();

    private static final String DEFAULT_COURSE_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_QUESTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/course-questions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CourseQuestionRepository courseQuestionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourseQuestionMockMvc;

    private CourseQuestion courseQuestion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseQuestion createEntity(EntityManager em) {
        CourseQuestion courseQuestion = new CourseQuestion().questionId(DEFAULT_QUESTION_ID).courseQuestion(DEFAULT_COURSE_QUESTION);
        return courseQuestion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseQuestion createUpdatedEntity(EntityManager em) {
        CourseQuestion courseQuestion = new CourseQuestion().questionId(UPDATED_QUESTION_ID).courseQuestion(UPDATED_COURSE_QUESTION);
        return courseQuestion;
    }

    @BeforeEach
    public void initTest() {
        courseQuestion = createEntity(em);
    }

    @Test
    @Transactional
    void createCourseQuestion() throws Exception {
        int databaseSizeBeforeCreate = courseQuestionRepository.findAll().size();
        // Create the CourseQuestion
        restCourseQuestionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseQuestion))
            )
            .andExpect(status().isCreated());

        // Validate the CourseQuestion in the database
        List<CourseQuestion> courseQuestionList = courseQuestionRepository.findAll();
        assertThat(courseQuestionList).hasSize(databaseSizeBeforeCreate + 1);
        CourseQuestion testCourseQuestion = courseQuestionList.get(courseQuestionList.size() - 1);
        assertThat(testCourseQuestion.getQuestionId()).isEqualTo(DEFAULT_QUESTION_ID);
        assertThat(testCourseQuestion.getCourseQuestion()).isEqualTo(DEFAULT_COURSE_QUESTION);
    }

    @Test
    @Transactional
    void createCourseQuestionWithExistingId() throws Exception {
        // Create the CourseQuestion with an existing ID
        courseQuestion.setId(1L);

        int databaseSizeBeforeCreate = courseQuestionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseQuestionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseQuestion))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseQuestion in the database
        List<CourseQuestion> courseQuestionList = courseQuestionRepository.findAll();
        assertThat(courseQuestionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCourseQuestions() throws Exception {
        // Initialize the database
        courseQuestionRepository.saveAndFlush(courseQuestion);

        // Get all the courseQuestionList
        restCourseQuestionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseQuestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].questionId").value(hasItem(DEFAULT_QUESTION_ID.toString())))
            .andExpect(jsonPath("$.[*].courseQuestion").value(hasItem(DEFAULT_COURSE_QUESTION)));
    }

    @Test
    @Transactional
    void getCourseQuestion() throws Exception {
        // Initialize the database
        courseQuestionRepository.saveAndFlush(courseQuestion);

        // Get the courseQuestion
        restCourseQuestionMockMvc
            .perform(get(ENTITY_API_URL_ID, courseQuestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(courseQuestion.getId().intValue()))
            .andExpect(jsonPath("$.questionId").value(DEFAULT_QUESTION_ID.toString()))
            .andExpect(jsonPath("$.courseQuestion").value(DEFAULT_COURSE_QUESTION));
    }

    @Test
    @Transactional
    void getNonExistingCourseQuestion() throws Exception {
        // Get the courseQuestion
        restCourseQuestionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCourseQuestion() throws Exception {
        // Initialize the database
        courseQuestionRepository.saveAndFlush(courseQuestion);

        int databaseSizeBeforeUpdate = courseQuestionRepository.findAll().size();

        // Update the courseQuestion
        CourseQuestion updatedCourseQuestion = courseQuestionRepository.findById(courseQuestion.getId()).get();
        // Disconnect from session so that the updates on updatedCourseQuestion are not directly saved in db
        em.detach(updatedCourseQuestion);
        updatedCourseQuestion.questionId(UPDATED_QUESTION_ID).courseQuestion(UPDATED_COURSE_QUESTION);

        restCourseQuestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCourseQuestion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCourseQuestion))
            )
            .andExpect(status().isOk());

        // Validate the CourseQuestion in the database
        List<CourseQuestion> courseQuestionList = courseQuestionRepository.findAll();
        assertThat(courseQuestionList).hasSize(databaseSizeBeforeUpdate);
        CourseQuestion testCourseQuestion = courseQuestionList.get(courseQuestionList.size() - 1);
        assertThat(testCourseQuestion.getQuestionId()).isEqualTo(UPDATED_QUESTION_ID);
        assertThat(testCourseQuestion.getCourseQuestion()).isEqualTo(UPDATED_COURSE_QUESTION);
    }

    @Test
    @Transactional
    void putNonExistingCourseQuestion() throws Exception {
        int databaseSizeBeforeUpdate = courseQuestionRepository.findAll().size();
        courseQuestion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseQuestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseQuestion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseQuestion))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseQuestion in the database
        List<CourseQuestion> courseQuestionList = courseQuestionRepository.findAll();
        assertThat(courseQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCourseQuestion() throws Exception {
        int databaseSizeBeforeUpdate = courseQuestionRepository.findAll().size();
        courseQuestion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseQuestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseQuestion))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseQuestion in the database
        List<CourseQuestion> courseQuestionList = courseQuestionRepository.findAll();
        assertThat(courseQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCourseQuestion() throws Exception {
        int databaseSizeBeforeUpdate = courseQuestionRepository.findAll().size();
        courseQuestion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseQuestionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseQuestion)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseQuestion in the database
        List<CourseQuestion> courseQuestionList = courseQuestionRepository.findAll();
        assertThat(courseQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCourseQuestionWithPatch() throws Exception {
        // Initialize the database
        courseQuestionRepository.saveAndFlush(courseQuestion);

        int databaseSizeBeforeUpdate = courseQuestionRepository.findAll().size();

        // Update the courseQuestion using partial update
        CourseQuestion partialUpdatedCourseQuestion = new CourseQuestion();
        partialUpdatedCourseQuestion.setId(courseQuestion.getId());

        restCourseQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseQuestion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseQuestion))
            )
            .andExpect(status().isOk());

        // Validate the CourseQuestion in the database
        List<CourseQuestion> courseQuestionList = courseQuestionRepository.findAll();
        assertThat(courseQuestionList).hasSize(databaseSizeBeforeUpdate);
        CourseQuestion testCourseQuestion = courseQuestionList.get(courseQuestionList.size() - 1);
        assertThat(testCourseQuestion.getQuestionId()).isEqualTo(DEFAULT_QUESTION_ID);
        assertThat(testCourseQuestion.getCourseQuestion()).isEqualTo(DEFAULT_COURSE_QUESTION);
    }

    @Test
    @Transactional
    void fullUpdateCourseQuestionWithPatch() throws Exception {
        // Initialize the database
        courseQuestionRepository.saveAndFlush(courseQuestion);

        int databaseSizeBeforeUpdate = courseQuestionRepository.findAll().size();

        // Update the courseQuestion using partial update
        CourseQuestion partialUpdatedCourseQuestion = new CourseQuestion();
        partialUpdatedCourseQuestion.setId(courseQuestion.getId());

        partialUpdatedCourseQuestion.questionId(UPDATED_QUESTION_ID).courseQuestion(UPDATED_COURSE_QUESTION);

        restCourseQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseQuestion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseQuestion))
            )
            .andExpect(status().isOk());

        // Validate the CourseQuestion in the database
        List<CourseQuestion> courseQuestionList = courseQuestionRepository.findAll();
        assertThat(courseQuestionList).hasSize(databaseSizeBeforeUpdate);
        CourseQuestion testCourseQuestion = courseQuestionList.get(courseQuestionList.size() - 1);
        assertThat(testCourseQuestion.getQuestionId()).isEqualTo(UPDATED_QUESTION_ID);
        assertThat(testCourseQuestion.getCourseQuestion()).isEqualTo(UPDATED_COURSE_QUESTION);
    }

    @Test
    @Transactional
    void patchNonExistingCourseQuestion() throws Exception {
        int databaseSizeBeforeUpdate = courseQuestionRepository.findAll().size();
        courseQuestion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, courseQuestion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseQuestion))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseQuestion in the database
        List<CourseQuestion> courseQuestionList = courseQuestionRepository.findAll();
        assertThat(courseQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCourseQuestion() throws Exception {
        int databaseSizeBeforeUpdate = courseQuestionRepository.findAll().size();
        courseQuestion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseQuestion))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseQuestion in the database
        List<CourseQuestion> courseQuestionList = courseQuestionRepository.findAll();
        assertThat(courseQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCourseQuestion() throws Exception {
        int databaseSizeBeforeUpdate = courseQuestionRepository.findAll().size();
        courseQuestion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(courseQuestion))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseQuestion in the database
        List<CourseQuestion> courseQuestionList = courseQuestionRepository.findAll();
        assertThat(courseQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCourseQuestion() throws Exception {
        // Initialize the database
        courseQuestionRepository.saveAndFlush(courseQuestion);

        int databaseSizeBeforeDelete = courseQuestionRepository.findAll().size();

        // Delete the courseQuestion
        restCourseQuestionMockMvc
            .perform(delete(ENTITY_API_URL_ID, courseQuestion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourseQuestion> courseQuestionList = courseQuestionRepository.findAll();
        assertThat(courseQuestionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
