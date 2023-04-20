package com.evaluar_cursos.web.rest;

import com.evaluar_cursos.domain.TotalScoreCourse;
import com.evaluar_cursos.repository.TotalScoreCourseRepository;
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
 * REST controller for managing {@link com.evaluar_cursos.domain.TotalScoreCourse}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TotalScoreCourseResource {

    private final Logger log = LoggerFactory.getLogger(TotalScoreCourseResource.class);

    private static final String ENTITY_NAME = "totalScoreCourse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TotalScoreCourseRepository totalScoreCourseRepository;

    public TotalScoreCourseResource(TotalScoreCourseRepository totalScoreCourseRepository) {
        this.totalScoreCourseRepository = totalScoreCourseRepository;
    }

    /**
     * {@code POST  /total-score-courses} : Create a new totalScoreCourse.
     *
     * @param totalScoreCourse the totalScoreCourse to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new totalScoreCourse, or with status {@code 400 (Bad Request)} if the totalScoreCourse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/total-score-courses")
    public ResponseEntity<TotalScoreCourse> createTotalScoreCourse(@RequestBody TotalScoreCourse totalScoreCourse)
        throws URISyntaxException {
        log.debug("REST request to save TotalScoreCourse : {}", totalScoreCourse);
        if (totalScoreCourse.getId() != null) {
            throw new BadRequestAlertException("A new totalScoreCourse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TotalScoreCourse result = totalScoreCourseRepository.save(totalScoreCourse);
        return ResponseEntity
            .created(new URI("/api/total-score-courses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /total-score-courses/:id} : Updates an existing totalScoreCourse.
     *
     * @param id the id of the totalScoreCourse to save.
     * @param totalScoreCourse the totalScoreCourse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated totalScoreCourse,
     * or with status {@code 400 (Bad Request)} if the totalScoreCourse is not valid,
     * or with status {@code 500 (Internal Server Error)} if the totalScoreCourse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/total-score-courses/{id}")
    public ResponseEntity<TotalScoreCourse> updateTotalScoreCourse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TotalScoreCourse totalScoreCourse
    ) throws URISyntaxException {
        log.debug("REST request to update TotalScoreCourse : {}, {}", id, totalScoreCourse);
        if (totalScoreCourse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, totalScoreCourse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!totalScoreCourseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TotalScoreCourse result = totalScoreCourseRepository.save(totalScoreCourse);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, totalScoreCourse.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /total-score-courses/:id} : Partial updates given fields of an existing totalScoreCourse, field will ignore if it is null
     *
     * @param id the id of the totalScoreCourse to save.
     * @param totalScoreCourse the totalScoreCourse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated totalScoreCourse,
     * or with status {@code 400 (Bad Request)} if the totalScoreCourse is not valid,
     * or with status {@code 404 (Not Found)} if the totalScoreCourse is not found,
     * or with status {@code 500 (Internal Server Error)} if the totalScoreCourse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/total-score-courses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TotalScoreCourse> partialUpdateTotalScoreCourse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TotalScoreCourse totalScoreCourse
    ) throws URISyntaxException {
        log.debug("REST request to partial update TotalScoreCourse partially : {}, {}", id, totalScoreCourse);
        if (totalScoreCourse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, totalScoreCourse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!totalScoreCourseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TotalScoreCourse> result = totalScoreCourseRepository
            .findById(totalScoreCourse.getId())
            .map(existingTotalScoreCourse -> {
                if (totalScoreCourse.getcAverageOne() != null) {
                    existingTotalScoreCourse.setcAverageOne(totalScoreCourse.getcAverageOne());
                }
                if (totalScoreCourse.getcAverageTwo() != null) {
                    existingTotalScoreCourse.setcAverageTwo(totalScoreCourse.getcAverageTwo());
                }
                if (totalScoreCourse.getcAverageThree() != null) {
                    existingTotalScoreCourse.setcAverageThree(totalScoreCourse.getcAverageThree());
                }
                if (totalScoreCourse.getcAverageFour() != null) {
                    existingTotalScoreCourse.setcAverageFour(totalScoreCourse.getcAverageFour());
                }
                if (totalScoreCourse.getcAverageFive() != null) {
                    existingTotalScoreCourse.setcAverageFive(totalScoreCourse.getcAverageFive());
                }

                return existingTotalScoreCourse;
            })
            .map(totalScoreCourseRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, totalScoreCourse.getId().toString())
        );
    }

    /**
     * {@code GET  /total-score-courses} : get all the totalScoreCourses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of totalScoreCourses in body.
     */
    @GetMapping("/total-score-courses")
    public List<TotalScoreCourse> getAllTotalScoreCourses() {
        log.debug("REST request to get all TotalScoreCourses");
        return totalScoreCourseRepository.findAll();
    }

    /**
     * {@code GET  /total-score-courses/:id} : get the "id" totalScoreCourse.
     *
     * @param id the id of the totalScoreCourse to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the totalScoreCourse, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/total-score-courses/{id}")
    public ResponseEntity<TotalScoreCourse> getTotalScoreCourse(@PathVariable Long id) {
        log.debug("REST request to get TotalScoreCourse : {}", id);
        Optional<TotalScoreCourse> totalScoreCourse = totalScoreCourseRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(totalScoreCourse);
    }

    /**
     * {@code DELETE  /total-score-courses/:id} : delete the "id" totalScoreCourse.
     *
     * @param id the id of the totalScoreCourse to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/total-score-courses/{id}")
    public ResponseEntity<Void> deleteTotalScoreCourse(@PathVariable Long id) {
        log.debug("REST request to delete TotalScoreCourse : {}", id);
        totalScoreCourseRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
