package com.evaluar_cursos.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.evaluar_cursos.IntegrationTest;
import com.evaluar_cursos.domain.EnrollCourse;
import com.evaluar_cursos.repository.EnrollCourseRepository;
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
 * Integration tests for the {@link EnrollCourseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EnrollCourseResourceIT {

    private static final String DEFAULT_PERIOD_ACADEMIC = "AAAAAAAAAA";
    private static final String UPDATED_PERIOD_ACADEMIC = "BBBBBBBBBB";

    private static final Integer DEFAULT_IS_EVALUATED = 1;
    private static final Integer UPDATED_IS_EVALUATED = 2;

    private static final String ENTITY_API_URL = "/api/enroll-courses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EnrollCourseRepository enrollCourseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnrollCourseMockMvc;

    private EnrollCourse enrollCourse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnrollCourse createEntity(EntityManager em) {
        EnrollCourse enrollCourse = new EnrollCourse().periodAcademic(DEFAULT_PERIOD_ACADEMIC).isEvaluated(DEFAULT_IS_EVALUATED);
        return enrollCourse;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnrollCourse createUpdatedEntity(EntityManager em) {
        EnrollCourse enrollCourse = new EnrollCourse().periodAcademic(UPDATED_PERIOD_ACADEMIC).isEvaluated(UPDATED_IS_EVALUATED);
        return enrollCourse;
    }

    @BeforeEach
    public void initTest() {
        enrollCourse = createEntity(em);
    }

    @Test
    @Transactional
    void createEnrollCourse() throws Exception {
        int databaseSizeBeforeCreate = enrollCourseRepository.findAll().size();
        // Create the EnrollCourse
        restEnrollCourseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enrollCourse)))
            .andExpect(status().isCreated());

        // Validate the EnrollCourse in the database
        List<EnrollCourse> enrollCourseList = enrollCourseRepository.findAll();
        assertThat(enrollCourseList).hasSize(databaseSizeBeforeCreate + 1);
        EnrollCourse testEnrollCourse = enrollCourseList.get(enrollCourseList.size() - 1);
        assertThat(testEnrollCourse.getPeriodAcademic()).isEqualTo(DEFAULT_PERIOD_ACADEMIC);
        assertThat(testEnrollCourse.getIsEvaluated()).isEqualTo(DEFAULT_IS_EVALUATED);
    }

    @Test
    @Transactional
    void createEnrollCourseWithExistingId() throws Exception {
        // Create the EnrollCourse with an existing ID
        enrollCourse.setId(1L);

        int databaseSizeBeforeCreate = enrollCourseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnrollCourseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enrollCourse)))
            .andExpect(status().isBadRequest());

        // Validate the EnrollCourse in the database
        List<EnrollCourse> enrollCourseList = enrollCourseRepository.findAll();
        assertThat(enrollCourseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEnrollCourses() throws Exception {
        // Initialize the database
        enrollCourseRepository.saveAndFlush(enrollCourse);

        // Get all the enrollCourseList
        restEnrollCourseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enrollCourse.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodAcademic").value(hasItem(DEFAULT_PERIOD_ACADEMIC)))
            .andExpect(jsonPath("$.[*].isEvaluated").value(hasItem(DEFAULT_IS_EVALUATED)));
    }

    @Test
    @Transactional
    void getEnrollCourse() throws Exception {
        // Initialize the database
        enrollCourseRepository.saveAndFlush(enrollCourse);

        // Get the enrollCourse
        restEnrollCourseMockMvc
            .perform(get(ENTITY_API_URL_ID, enrollCourse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(enrollCourse.getId().intValue()))
            .andExpect(jsonPath("$.periodAcademic").value(DEFAULT_PERIOD_ACADEMIC))
            .andExpect(jsonPath("$.isEvaluated").value(DEFAULT_IS_EVALUATED));
    }

    @Test
    @Transactional
    void getNonExistingEnrollCourse() throws Exception {
        // Get the enrollCourse
        restEnrollCourseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEnrollCourse() throws Exception {
        // Initialize the database
        enrollCourseRepository.saveAndFlush(enrollCourse);

        int databaseSizeBeforeUpdate = enrollCourseRepository.findAll().size();

        // Update the enrollCourse
        EnrollCourse updatedEnrollCourse = enrollCourseRepository.findById(enrollCourse.getId()).get();
        // Disconnect from session so that the updates on updatedEnrollCourse are not directly saved in db
        em.detach(updatedEnrollCourse);
        updatedEnrollCourse.periodAcademic(UPDATED_PERIOD_ACADEMIC).isEvaluated(UPDATED_IS_EVALUATED);

        restEnrollCourseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEnrollCourse.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEnrollCourse))
            )
            .andExpect(status().isOk());

        // Validate the EnrollCourse in the database
        List<EnrollCourse> enrollCourseList = enrollCourseRepository.findAll();
        assertThat(enrollCourseList).hasSize(databaseSizeBeforeUpdate);
        EnrollCourse testEnrollCourse = enrollCourseList.get(enrollCourseList.size() - 1);
        assertThat(testEnrollCourse.getPeriodAcademic()).isEqualTo(UPDATED_PERIOD_ACADEMIC);
        assertThat(testEnrollCourse.getIsEvaluated()).isEqualTo(UPDATED_IS_EVALUATED);
    }

    @Test
    @Transactional
    void putNonExistingEnrollCourse() throws Exception {
        int databaseSizeBeforeUpdate = enrollCourseRepository.findAll().size();
        enrollCourse.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnrollCourseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, enrollCourse.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(enrollCourse))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnrollCourse in the database
        List<EnrollCourse> enrollCourseList = enrollCourseRepository.findAll();
        assertThat(enrollCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEnrollCourse() throws Exception {
        int databaseSizeBeforeUpdate = enrollCourseRepository.findAll().size();
        enrollCourse.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnrollCourseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(enrollCourse))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnrollCourse in the database
        List<EnrollCourse> enrollCourseList = enrollCourseRepository.findAll();
        assertThat(enrollCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEnrollCourse() throws Exception {
        int databaseSizeBeforeUpdate = enrollCourseRepository.findAll().size();
        enrollCourse.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnrollCourseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enrollCourse)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EnrollCourse in the database
        List<EnrollCourse> enrollCourseList = enrollCourseRepository.findAll();
        assertThat(enrollCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEnrollCourseWithPatch() throws Exception {
        // Initialize the database
        enrollCourseRepository.saveAndFlush(enrollCourse);

        int databaseSizeBeforeUpdate = enrollCourseRepository.findAll().size();

        // Update the enrollCourse using partial update
        EnrollCourse partialUpdatedEnrollCourse = new EnrollCourse();
        partialUpdatedEnrollCourse.setId(enrollCourse.getId());

        restEnrollCourseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnrollCourse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEnrollCourse))
            )
            .andExpect(status().isOk());

        // Validate the EnrollCourse in the database
        List<EnrollCourse> enrollCourseList = enrollCourseRepository.findAll();
        assertThat(enrollCourseList).hasSize(databaseSizeBeforeUpdate);
        EnrollCourse testEnrollCourse = enrollCourseList.get(enrollCourseList.size() - 1);
        assertThat(testEnrollCourse.getPeriodAcademic()).isEqualTo(DEFAULT_PERIOD_ACADEMIC);
        assertThat(testEnrollCourse.getIsEvaluated()).isEqualTo(DEFAULT_IS_EVALUATED);
    }

    @Test
    @Transactional
    void fullUpdateEnrollCourseWithPatch() throws Exception {
        // Initialize the database
        enrollCourseRepository.saveAndFlush(enrollCourse);

        int databaseSizeBeforeUpdate = enrollCourseRepository.findAll().size();

        // Update the enrollCourse using partial update
        EnrollCourse partialUpdatedEnrollCourse = new EnrollCourse();
        partialUpdatedEnrollCourse.setId(enrollCourse.getId());

        partialUpdatedEnrollCourse.periodAcademic(UPDATED_PERIOD_ACADEMIC).isEvaluated(UPDATED_IS_EVALUATED);

        restEnrollCourseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnrollCourse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEnrollCourse))
            )
            .andExpect(status().isOk());

        // Validate the EnrollCourse in the database
        List<EnrollCourse> enrollCourseList = enrollCourseRepository.findAll();
        assertThat(enrollCourseList).hasSize(databaseSizeBeforeUpdate);
        EnrollCourse testEnrollCourse = enrollCourseList.get(enrollCourseList.size() - 1);
        assertThat(testEnrollCourse.getPeriodAcademic()).isEqualTo(UPDATED_PERIOD_ACADEMIC);
        assertThat(testEnrollCourse.getIsEvaluated()).isEqualTo(UPDATED_IS_EVALUATED);
    }

    @Test
    @Transactional
    void patchNonExistingEnrollCourse() throws Exception {
        int databaseSizeBeforeUpdate = enrollCourseRepository.findAll().size();
        enrollCourse.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnrollCourseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, enrollCourse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(enrollCourse))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnrollCourse in the database
        List<EnrollCourse> enrollCourseList = enrollCourseRepository.findAll();
        assertThat(enrollCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEnrollCourse() throws Exception {
        int databaseSizeBeforeUpdate = enrollCourseRepository.findAll().size();
        enrollCourse.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnrollCourseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(enrollCourse))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnrollCourse in the database
        List<EnrollCourse> enrollCourseList = enrollCourseRepository.findAll();
        assertThat(enrollCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEnrollCourse() throws Exception {
        int databaseSizeBeforeUpdate = enrollCourseRepository.findAll().size();
        enrollCourse.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnrollCourseMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(enrollCourse))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EnrollCourse in the database
        List<EnrollCourse> enrollCourseList = enrollCourseRepository.findAll();
        assertThat(enrollCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEnrollCourse() throws Exception {
        // Initialize the database
        enrollCourseRepository.saveAndFlush(enrollCourse);

        int databaseSizeBeforeDelete = enrollCourseRepository.findAll().size();

        // Delete the enrollCourse
        restEnrollCourseMockMvc
            .perform(delete(ENTITY_API_URL_ID, enrollCourse.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EnrollCourse> enrollCourseList = enrollCourseRepository.findAll();
        assertThat(enrollCourseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
