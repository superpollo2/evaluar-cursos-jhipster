package com.evaluar_cursos.web.rest;

import com.evaluar_cursos.domain.CourseQuestion;
import com.evaluar_cursos.repository.CourseQuestionRepository;
import com.evaluar_cursos.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.evaluar_cursos.domain.CourseQuestion}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CourseQuestionResource {

    private final Logger log = LoggerFactory.getLogger(CourseQuestionResource.class);

    private static final String ENTITY_NAME = "courseQuestion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourseQuestionRepository courseQuestionRepository;

    public CourseQuestionResource(CourseQuestionRepository courseQuestionRepository) {
        this.courseQuestionRepository = courseQuestionRepository;
    }

    /**
     * {@code POST  /course-questions} : Create a new courseQuestion.
     *
     * @param courseQuestion the courseQuestion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courseQuestion, or with status {@code 400 (Bad Request)} if the courseQuestion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/course-questions")
    public ResponseEntity<CourseQuestion> createCourseQuestion(@RequestBody CourseQuestion courseQuestion) throws URISyntaxException {
        log.debug("REST request to save CourseQuestion : {}", courseQuestion);
        if (courseQuestion.getId() != null) {
            throw new BadRequestAlertException("A new courseQuestion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourseQuestion result = courseQuestionRepository.save(courseQuestion);
        return ResponseEntity
            .created(new URI("/api/course-questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /course-questions/:id} : Updates an existing courseQuestion.
     *
     * @param id the id of the courseQuestion to save.
     * @param courseQuestion the courseQuestion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseQuestion,
     * or with status {@code 400 (Bad Request)} if the courseQuestion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courseQuestion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/course-questions/{id}")
    public ResponseEntity<CourseQuestion> updateCourseQuestion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CourseQuestion courseQuestion
    ) throws URISyntaxException {
        log.debug("REST request to update CourseQuestion : {}, {}", id, courseQuestion);
        if (courseQuestion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseQuestion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseQuestionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CourseQuestion result = courseQuestionRepository.save(courseQuestion);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseQuestion.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /course-questions/:id} : Partial updates given fields of an existing courseQuestion, field will ignore if it is null
     *
     * @param id the id of the courseQuestion to save.
     * @param courseQuestion the courseQuestion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseQuestion,
     * or with status {@code 400 (Bad Request)} if the courseQuestion is not valid,
     * or with status {@code 404 (Not Found)} if the courseQuestion is not found,
     * or with status {@code 500 (Internal Server Error)} if the courseQuestion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/course-questions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CourseQuestion> partialUpdateCourseQuestion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CourseQuestion courseQuestion
    ) throws URISyntaxException {
        log.debug("REST request to partial update CourseQuestion partially : {}, {}", id, courseQuestion);
        if (courseQuestion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseQuestion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseQuestionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CourseQuestion> result = courseQuestionRepository
            .findById(courseQuestion.getId())
            .map(existingCourseQuestion -> {
                if (courseQuestion.getQuestionId() != null) {
                    existingCourseQuestion.setQuestionId(courseQuestion.getQuestionId());
                }
                if (courseQuestion.getCourseQuestion() != null) {
                    existingCourseQuestion.setCourseQuestion(courseQuestion.getCourseQuestion());
                }

                return existingCourseQuestion;
            })
            .map(courseQuestionRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseQuestion.getId().toString())
        );
    }

    /**
     * {@code GET  /course-questions} : get all the courseQuestions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courseQuestions in body.
     */
    @GetMapping("/course-questions")
    public List<CourseQuestion> getAllCourseQuestions() {
        log.debug("REST request to get all CourseQuestions");
        return courseQuestionRepository.findAll();
    }

    /**
     * {@code GET  /course-questions/:id} : get the "id" courseQuestion.
     *
     * @param id the id of the courseQuestion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courseQuestion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/course-questions/{id}")
    public ResponseEntity<CourseQuestion> getCourseQuestion(@PathVariable Long id) {
        log.debug("REST request to get CourseQuestion : {}", id);
        Optional<CourseQuestion> courseQuestion = courseQuestionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(courseQuestion);
    }

    /**
     * {@code DELETE  /course-questions/:id} : delete the "id" courseQuestion.
     *
     * @param id the id of the courseQuestion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/course-questions/{id}")
    public ResponseEntity<Void> deleteCourseQuestion(@PathVariable Long id) {
        log.debug("REST request to delete CourseQuestion : {}", id);
        courseQuestionRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
