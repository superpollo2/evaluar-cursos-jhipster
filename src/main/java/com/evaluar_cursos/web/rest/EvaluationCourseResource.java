package com.evaluar_cursos.web.rest;

import com.evaluar_cursos.domain.EvaluationCourse;
import com.evaluar_cursos.repository.EvaluationCourseRepository;
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
 * REST controller for managing {@link com.evaluar_cursos.domain.EvaluationCourse}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EvaluationCourseResource {

    private final Logger log = LoggerFactory.getLogger(EvaluationCourseResource.class);

    private static final String ENTITY_NAME = "evaluationCourse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EvaluationCourseRepository evaluationCourseRepository;

    public EvaluationCourseResource(EvaluationCourseRepository evaluationCourseRepository) {
        this.evaluationCourseRepository = evaluationCourseRepository;
    }

    /**
     * {@code POST  /evaluation-courses} : Create a new evaluationCourse.
     *
     * @param evaluationCourse the evaluationCourse to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new evaluationCourse, or with status {@code 400 (Bad Request)} if the evaluationCourse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/evaluation-courses")
    public ResponseEntity<EvaluationCourse> createEvaluationCourse(@RequestBody EvaluationCourse evaluationCourse)
        throws URISyntaxException {
        log.debug("REST request to save EvaluationCourse : {}", evaluationCourse);
        if (evaluationCourse.getId() != null) {
            throw new BadRequestAlertException("A new evaluationCourse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EvaluationCourse result = evaluationCourseRepository.save(evaluationCourse);
        return ResponseEntity
            .created(new URI("/api/evaluation-courses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /evaluation-courses/:id} : Updates an existing evaluationCourse.
     *
     * @param id the id of the evaluationCourse to save.
     * @param evaluationCourse the evaluationCourse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evaluationCourse,
     * or with status {@code 400 (Bad Request)} if the evaluationCourse is not valid,
     * or with status {@code 500 (Internal Server Error)} if the evaluationCourse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/evaluation-courses/{id}")
    public ResponseEntity<EvaluationCourse> updateEvaluationCourse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EvaluationCourse evaluationCourse
    ) throws URISyntaxException {
        log.debug("REST request to update EvaluationCourse : {}, {}", id, evaluationCourse);
        if (evaluationCourse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, evaluationCourse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!evaluationCourseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EvaluationCourse result = evaluationCourseRepository.save(evaluationCourse);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, evaluationCourse.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /evaluation-courses/:id} : Partial updates given fields of an existing evaluationCourse, field will ignore if it is null
     *
     * @param id the id of the evaluationCourse to save.
     * @param evaluationCourse the evaluationCourse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evaluationCourse,
     * or with status {@code 400 (Bad Request)} if the evaluationCourse is not valid,
     * or with status {@code 404 (Not Found)} if the evaluationCourse is not found,
     * or with status {@code 500 (Internal Server Error)} if the evaluationCourse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/evaluation-courses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EvaluationCourse> partialUpdateEvaluationCourse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EvaluationCourse evaluationCourse
    ) throws URISyntaxException {
        log.debug("REST request to partial update EvaluationCourse partially : {}, {}", id, evaluationCourse);
        if (evaluationCourse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, evaluationCourse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!evaluationCourseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EvaluationCourse> result = evaluationCourseRepository
            .findById(evaluationCourse.getId())
            .map(existingEvaluationCourse -> {
                if (evaluationCourse.getcScoreQuestionOne() != null) {
                    existingEvaluationCourse.setcScoreQuestionOne(evaluationCourse.getcScoreQuestionOne());
                }
                if (evaluationCourse.getcScoreQuestionTwo() != null) {
                    existingEvaluationCourse.setcScoreQuestionTwo(evaluationCourse.getcScoreQuestionTwo());
                }
                if (evaluationCourse.getcScoreQuestionThree() != null) {
                    existingEvaluationCourse.setcScoreQuestionThree(evaluationCourse.getcScoreQuestionThree());
                }
                if (evaluationCourse.getcScoreQuestionFour() != null) {
                    existingEvaluationCourse.setcScoreQuestionFour(evaluationCourse.getcScoreQuestionFour());
                }
                if (evaluationCourse.getcScoreQuestionFive() != null) {
                    existingEvaluationCourse.setcScoreQuestionFive(evaluationCourse.getcScoreQuestionFive());
                }
                if (evaluationCourse.getcScoreQuestionSix() != null) {
                    existingEvaluationCourse.setcScoreQuestionSix(evaluationCourse.getcScoreQuestionSix());
                }

                return existingEvaluationCourse;
            })
            .map(evaluationCourseRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, evaluationCourse.getId().toString())
        );
    }

    /**
     * {@code GET  /evaluation-courses} : get all the evaluationCourses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of evaluationCourses in body.
     */
    @GetMapping("/evaluation-courses")
    public List<EvaluationCourse> getAllEvaluationCourses() {
        log.debug("REST request to get all EvaluationCourses");
        return evaluationCourseRepository.findAll();
    }

    /**
     * {@code GET  /evaluation-courses/:id} : get the "id" evaluationCourse.
     *
     * @param id the id of the evaluationCourse to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the evaluationCourse, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/evaluation-courses/{id}")
    public ResponseEntity<EvaluationCourse> getEvaluationCourse(@PathVariable Long id) {
        log.debug("REST request to get EvaluationCourse : {}", id);
        Optional<EvaluationCourse> evaluationCourse = evaluationCourseRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(evaluationCourse);
    }

    /**
     * {@code DELETE  /evaluation-courses/:id} : delete the "id" evaluationCourse.
     *
     * @param id the id of the evaluationCourse to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/evaluation-courses/{id}")
    public ResponseEntity<Void> deleteEvaluationCourse(@PathVariable Long id) {
        log.debug("REST request to delete EvaluationCourse : {}", id);
        evaluationCourseRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
