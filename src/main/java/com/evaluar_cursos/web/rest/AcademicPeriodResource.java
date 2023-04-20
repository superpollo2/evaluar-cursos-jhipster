package com.evaluar_cursos.web.rest;

import com.evaluar_cursos.domain.AcademicPeriod;
import com.evaluar_cursos.repository.AcademicPeriodRepository;
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
 * REST controller for managing {@link com.evaluar_cursos.domain.AcademicPeriod}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AcademicPeriodResource {

    private final Logger log = LoggerFactory.getLogger(AcademicPeriodResource.class);

    private static final String ENTITY_NAME = "academicPeriod";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AcademicPeriodRepository academicPeriodRepository;

    public AcademicPeriodResource(AcademicPeriodRepository academicPeriodRepository) {
        this.academicPeriodRepository = academicPeriodRepository;
    }

    /**
     * {@code POST  /academic-periods} : Create a new academicPeriod.
     *
     * @param academicPeriod the academicPeriod to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new academicPeriod, or with status {@code 400 (Bad Request)} if the academicPeriod has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/academic-periods")
    public ResponseEntity<AcademicPeriod> createAcademicPeriod(@RequestBody AcademicPeriod academicPeriod) throws URISyntaxException {
        log.debug("REST request to save AcademicPeriod : {}", academicPeriod);
        if (academicPeriod.getId() != null) {
            throw new BadRequestAlertException("A new academicPeriod cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AcademicPeriod result = academicPeriodRepository.save(academicPeriod);
        return ResponseEntity
            .created(new URI("/api/academic-periods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /academic-periods/:id} : Updates an existing academicPeriod.
     *
     * @param id the id of the academicPeriod to save.
     * @param academicPeriod the academicPeriod to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated academicPeriod,
     * or with status {@code 400 (Bad Request)} if the academicPeriod is not valid,
     * or with status {@code 500 (Internal Server Error)} if the academicPeriod couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/academic-periods/{id}")
    public ResponseEntity<AcademicPeriod> updateAcademicPeriod(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AcademicPeriod academicPeriod
    ) throws URISyntaxException {
        log.debug("REST request to update AcademicPeriod : {}, {}", id, academicPeriod);
        if (academicPeriod.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, academicPeriod.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!academicPeriodRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AcademicPeriod result = academicPeriodRepository.save(academicPeriod);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, academicPeriod.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /academic-periods/:id} : Partial updates given fields of an existing academicPeriod, field will ignore if it is null
     *
     * @param id the id of the academicPeriod to save.
     * @param academicPeriod the academicPeriod to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated academicPeriod,
     * or with status {@code 400 (Bad Request)} if the academicPeriod is not valid,
     * or with status {@code 404 (Not Found)} if the academicPeriod is not found,
     * or with status {@code 500 (Internal Server Error)} if the academicPeriod couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/academic-periods/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AcademicPeriod> partialUpdateAcademicPeriod(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AcademicPeriod academicPeriod
    ) throws URISyntaxException {
        log.debug("REST request to partial update AcademicPeriod partially : {}, {}", id, academicPeriod);
        if (academicPeriod.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, academicPeriod.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!academicPeriodRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AcademicPeriod> result = academicPeriodRepository
            .findById(academicPeriod.getId())
            .map(existingAcademicPeriod -> {
                if (academicPeriod.getInitPeriod() != null) {
                    existingAcademicPeriod.setInitPeriod(academicPeriod.getInitPeriod());
                }
                if (academicPeriod.getFinishPeriod() != null) {
                    existingAcademicPeriod.setFinishPeriod(academicPeriod.getFinishPeriod());
                }

                return existingAcademicPeriod;
            })
            .map(academicPeriodRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, academicPeriod.getId().toString())
        );
    }

    /**
     * {@code GET  /academic-periods} : get all the academicPeriods.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of academicPeriods in body.
     */
    @GetMapping("/academic-periods")
    public List<AcademicPeriod> getAllAcademicPeriods() {
        log.debug("REST request to get all AcademicPeriods");
        return academicPeriodRepository.findAll();
    }

    /**
     * {@code GET  /academic-periods/:id} : get the "id" academicPeriod.
     *
     * @param id the id of the academicPeriod to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the academicPeriod, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/academic-periods/{id}")
    public ResponseEntity<AcademicPeriod> getAcademicPeriod(@PathVariable Long id) {
        log.debug("REST request to get AcademicPeriod : {}", id);
        Optional<AcademicPeriod> academicPeriod = academicPeriodRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(academicPeriod);
    }

    /**
     * {@code DELETE  /academic-periods/:id} : delete the "id" academicPeriod.
     *
     * @param id the id of the academicPeriod to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/academic-periods/{id}")
    public ResponseEntity<Void> deleteAcademicPeriod(@PathVariable Long id) {
        log.debug("REST request to delete AcademicPeriod : {}", id);
        academicPeriodRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
