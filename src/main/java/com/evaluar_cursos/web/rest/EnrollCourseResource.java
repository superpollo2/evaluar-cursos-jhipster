package com.evaluar_cursos.web.rest;

import com.evaluar_cursos.domain.EnrollCourse;
import com.evaluar_cursos.repository.EnrollCourseRepository;
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
 * REST controller for managing {@link com.evaluar_cursos.domain.EnrollCourse}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EnrollCourseResource {

    private final Logger log = LoggerFactory.getLogger(EnrollCourseResource.class);

    private static final String ENTITY_NAME = "enrollCourse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnrollCourseRepository enrollCourseRepository;

    public EnrollCourseResource(EnrollCourseRepository enrollCourseRepository) {
        this.enrollCourseRepository = enrollCourseRepository;
    }

    /**
     * {@code POST  /enroll-courses} : Create a new enrollCourse.
     *
     * @param enrollCourse the enrollCourse to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enrollCourse, or with status {@code 400 (Bad Request)} if the enrollCourse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/enroll-courses")
    public ResponseEntity<EnrollCourse> createEnrollCourse(@RequestBody EnrollCourse enrollCourse) throws URISyntaxException {
        log.debug("REST request to save EnrollCourse : {}", enrollCourse);
        if (enrollCourse.getId() != null) {
            throw new BadRequestAlertException("A new enrollCourse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnrollCourse result = enrollCourseRepository.save(enrollCourse);
        return ResponseEntity
            .created(new URI("/api/enroll-courses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /enroll-courses/:id} : Updates an existing enrollCourse.
     *
     * @param id the id of the enrollCourse to save.
     * @param enrollCourse the enrollCourse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enrollCourse,
     * or with status {@code 400 (Bad Request)} if the enrollCourse is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enrollCourse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/enroll-courses/{id}")
    public ResponseEntity<EnrollCourse> updateEnrollCourse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EnrollCourse enrollCourse
    ) throws URISyntaxException {
        log.debug("REST request to update EnrollCourse : {}, {}", id, enrollCourse);
        if (enrollCourse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enrollCourse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enrollCourseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EnrollCourse result = enrollCourseRepository.save(enrollCourse);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enrollCourse.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /enroll-courses/:id} : Partial updates given fields of an existing enrollCourse, field will ignore if it is null
     *
     * @param id the id of the enrollCourse to save.
     * @param enrollCourse the enrollCourse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enrollCourse,
     * or with status {@code 400 (Bad Request)} if the enrollCourse is not valid,
     * or with status {@code 404 (Not Found)} if the enrollCourse is not found,
     * or with status {@code 500 (Internal Server Error)} if the enrollCourse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/enroll-courses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EnrollCourse> partialUpdateEnrollCourse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EnrollCourse enrollCourse
    ) throws URISyntaxException {
        log.debug("REST request to partial update EnrollCourse partially : {}, {}", id, enrollCourse);
        if (enrollCourse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enrollCourse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enrollCourseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EnrollCourse> result = enrollCourseRepository
            .findById(enrollCourse.getId())
            .map(existingEnrollCourse -> {
                if (enrollCourse.getEnrollId() != null) {
                    existingEnrollCourse.setEnrollId(enrollCourse.getEnrollId());
                }
                if (enrollCourse.getPeriodAcademic() != null) {
                    existingEnrollCourse.setPeriodAcademic(enrollCourse.getPeriodAcademic());
                }
                if (enrollCourse.getIsEvaluated() != null) {
                    existingEnrollCourse.setIsEvaluated(enrollCourse.getIsEvaluated());
                }

                return existingEnrollCourse;
            })
            .map(enrollCourseRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enrollCourse.getId().toString())
        );
    }

    /**
     * {@code GET  /enroll-courses} : get all the enrollCourses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enrollCourses in body.
     */
    @GetMapping("/enroll-courses")
    public List<EnrollCourse> getAllEnrollCourses() {
        log.debug("REST request to get all EnrollCourses");
        return enrollCourseRepository.findAll();
    }

    /**
     * {@code GET  /enroll-courses/:id} : get the "id" enrollCourse.
     *
     * @param id the id of the enrollCourse to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enrollCourse, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/enroll-courses/{id}")
    public ResponseEntity<EnrollCourse> getEnrollCourse(@PathVariable Long id) {
        log.debug("REST request to get EnrollCourse : {}", id);
        Optional<EnrollCourse> enrollCourse = enrollCourseRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(enrollCourse);
    }

    /**
     * {@code DELETE  /enroll-courses/:id} : delete the "id" enrollCourse.
     *
     * @param id the id of the enrollCourse to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/enroll-courses/{id}")
    public ResponseEntity<Void> deleteEnrollCourse(@PathVariable Long id) {
        log.debug("REST request to delete EnrollCourse : {}", id);
        enrollCourseRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
