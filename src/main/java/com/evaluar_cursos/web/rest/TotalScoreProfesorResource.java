package com.evaluar_cursos.web.rest;

import com.evaluar_cursos.domain.TotalScoreProfesor;
import com.evaluar_cursos.repository.TotalScoreProfesorRepository;
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
 * REST controller for managing {@link com.evaluar_cursos.domain.TotalScoreProfesor}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TotalScoreProfesorResource {

    private final Logger log = LoggerFactory.getLogger(TotalScoreProfesorResource.class);

    private static final String ENTITY_NAME = "totalScoreProfesor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TotalScoreProfesorRepository totalScoreProfesorRepository;

    public TotalScoreProfesorResource(TotalScoreProfesorRepository totalScoreProfesorRepository) {
        this.totalScoreProfesorRepository = totalScoreProfesorRepository;
    }

    /**
     * {@code POST  /total-score-profesors} : Create a new totalScoreProfesor.
     *
     * @param totalScoreProfesor the totalScoreProfesor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new totalScoreProfesor, or with status {@code 400 (Bad Request)} if the totalScoreProfesor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/total-score-profesors")
    public ResponseEntity<TotalScoreProfesor> createTotalScoreProfesor(@RequestBody TotalScoreProfesor totalScoreProfesor)
        throws URISyntaxException {
        log.debug("REST request to save TotalScoreProfesor : {}", totalScoreProfesor);
        if (totalScoreProfesor.getId() != null) {
            throw new BadRequestAlertException("A new totalScoreProfesor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TotalScoreProfesor result = totalScoreProfesorRepository.save(totalScoreProfesor);
        return ResponseEntity
            .created(new URI("/api/total-score-profesors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /total-score-profesors/:id} : Updates an existing totalScoreProfesor.
     *
     * @param id the id of the totalScoreProfesor to save.
     * @param totalScoreProfesor the totalScoreProfesor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated totalScoreProfesor,
     * or with status {@code 400 (Bad Request)} if the totalScoreProfesor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the totalScoreProfesor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/total-score-profesors/{id}")
    public ResponseEntity<TotalScoreProfesor> updateTotalScoreProfesor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TotalScoreProfesor totalScoreProfesor
    ) throws URISyntaxException {
        log.debug("REST request to update TotalScoreProfesor : {}, {}", id, totalScoreProfesor);
        if (totalScoreProfesor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, totalScoreProfesor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!totalScoreProfesorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TotalScoreProfesor result = totalScoreProfesorRepository.save(totalScoreProfesor);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, totalScoreProfesor.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /total-score-profesors/:id} : Partial updates given fields of an existing totalScoreProfesor, field will ignore if it is null
     *
     * @param id the id of the totalScoreProfesor to save.
     * @param totalScoreProfesor the totalScoreProfesor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated totalScoreProfesor,
     * or with status {@code 400 (Bad Request)} if the totalScoreProfesor is not valid,
     * or with status {@code 404 (Not Found)} if the totalScoreProfesor is not found,
     * or with status {@code 500 (Internal Server Error)} if the totalScoreProfesor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/total-score-profesors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TotalScoreProfesor> partialUpdateTotalScoreProfesor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TotalScoreProfesor totalScoreProfesor
    ) throws URISyntaxException {
        log.debug("REST request to partial update TotalScoreProfesor partially : {}, {}", id, totalScoreProfesor);
        if (totalScoreProfesor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, totalScoreProfesor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!totalScoreProfesorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TotalScoreProfesor> result = totalScoreProfesorRepository
            .findById(totalScoreProfesor.getId())
            .map(existingTotalScoreProfesor -> {
                if (totalScoreProfesor.getpAverageOne() != null) {
                    existingTotalScoreProfesor.setpAverageOne(totalScoreProfesor.getpAverageOne());
                }
                if (totalScoreProfesor.getpAverageTwo() != null) {
                    existingTotalScoreProfesor.setpAverageTwo(totalScoreProfesor.getpAverageTwo());
                }
                if (totalScoreProfesor.getpAverageThree() != null) {
                    existingTotalScoreProfesor.setpAverageThree(totalScoreProfesor.getpAverageThree());
                }
                if (totalScoreProfesor.getpAverageFour() != null) {
                    existingTotalScoreProfesor.setpAverageFour(totalScoreProfesor.getpAverageFour());
                }
                if (totalScoreProfesor.getpAverageFive() != null) {
                    existingTotalScoreProfesor.setpAverageFive(totalScoreProfesor.getpAverageFive());
                }

                return existingTotalScoreProfesor;
            })
            .map(totalScoreProfesorRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, totalScoreProfesor.getId().toString())
        );
    }

    /**
     * {@code GET  /total-score-profesors} : get all the totalScoreProfesors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of totalScoreProfesors in body.
     */
    @GetMapping("/total-score-profesors")
    public List<TotalScoreProfesor> getAllTotalScoreProfesors() {
        log.debug("REST request to get all TotalScoreProfesors");
        return totalScoreProfesorRepository.findAll();
    }

    /**
     * {@code GET  /total-score-profesors/:id} : get the "id" totalScoreProfesor.
     *
     * @param id the id of the totalScoreProfesor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the totalScoreProfesor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/total-score-profesors/{id}")
    public ResponseEntity<TotalScoreProfesor> getTotalScoreProfesor(@PathVariable Long id) {
        log.debug("REST request to get TotalScoreProfesor : {}", id);
        Optional<TotalScoreProfesor> totalScoreProfesor = totalScoreProfesorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(totalScoreProfesor);
    }

    /**
     * {@code DELETE  /total-score-profesors/:id} : delete the "id" totalScoreProfesor.
     *
     * @param id the id of the totalScoreProfesor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/total-score-profesors/{id}")
    public ResponseEntity<Void> deleteTotalScoreProfesor(@PathVariable Long id) {
        log.debug("REST request to delete TotalScoreProfesor : {}", id);
        totalScoreProfesorRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
