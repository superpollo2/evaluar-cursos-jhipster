package com.evaluar_cursos.web.rest;

import com.evaluar_cursos.domain.AcademicProgram;
import com.evaluar_cursos.repository.AcademicProgramRepository;
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
 * REST controller for managing {@link com.evaluar_cursos.domain.AcademicProgram}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AcademicProgramResource {

    private final Logger log = LoggerFactory.getLogger(AcademicProgramResource.class);

    private static final String ENTITY_NAME = "academicProgram";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AcademicProgramRepository academicProgramRepository;

    public AcademicProgramResource(AcademicProgramRepository academicProgramRepository) {
        this.academicProgramRepository = academicProgramRepository;
    }

    /**
     * {@code POST  /academic-programs} : Create a new academicProgram.
     *
     * @param academicProgram the academicProgram to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new academicProgram, or with status {@code 400 (Bad Request)} if the academicProgram has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/academic-programs")
    public ResponseEntity<AcademicProgram> createAcademicProgram(@RequestBody AcademicProgram academicProgram) throws URISyntaxException {
        log.debug("REST request to save AcademicProgram : {}", academicProgram);
        if (academicProgram.getId() != null) {
            throw new BadRequestAlertException("A new academicProgram cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AcademicProgram result = academicProgramRepository.save(academicProgram);
        return ResponseEntity
            .created(new URI("/api/academic-programs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /academic-programs/:id} : Updates an existing academicProgram.
     *
     * @param id the id of the academicProgram to save.
     * @param academicProgram the academicProgram to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated academicProgram,
     * or with status {@code 400 (Bad Request)} if the academicProgram is not valid,
     * or with status {@code 500 (Internal Server Error)} if the academicProgram couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/academic-programs/{id}")
    public ResponseEntity<AcademicProgram> updateAcademicProgram(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AcademicProgram academicProgram
    ) throws URISyntaxException {
        log.debug("REST request to update AcademicProgram : {}, {}", id, academicProgram);
        if (academicProgram.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, academicProgram.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!academicProgramRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AcademicProgram result = academicProgramRepository.save(academicProgram);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, academicProgram.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /academic-programs/:id} : Partial updates given fields of an existing academicProgram, field will ignore if it is null
     *
     * @param id the id of the academicProgram to save.
     * @param academicProgram the academicProgram to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated academicProgram,
     * or with status {@code 400 (Bad Request)} if the academicProgram is not valid,
     * or with status {@code 404 (Not Found)} if the academicProgram is not found,
     * or with status {@code 500 (Internal Server Error)} if the academicProgram couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/academic-programs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AcademicProgram> partialUpdateAcademicProgram(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AcademicProgram academicProgram
    ) throws URISyntaxException {
        log.debug("REST request to partial update AcademicProgram partially : {}, {}", id, academicProgram);
        if (academicProgram.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, academicProgram.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!academicProgramRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AcademicProgram> result = academicProgramRepository
            .findById(academicProgram.getId())
            .map(existingAcademicProgram -> {
                if (academicProgram.getProgramId() != null) {
                    existingAcademicProgram.setProgramId(academicProgram.getProgramId());
                }
                if (academicProgram.getProgramName() != null) {
                    existingAcademicProgram.setProgramName(academicProgram.getProgramName());
                }

                return existingAcademicProgram;
            })
            .map(academicProgramRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, academicProgram.getId().toString())
        );
    }

    /**
     * {@code GET  /academic-programs} : get all the academicPrograms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of academicPrograms in body.
     */
    @GetMapping("/academic-programs")
    public List<AcademicProgram> getAllAcademicPrograms() {
        log.debug("REST request to get all AcademicPrograms");
        return academicProgramRepository.findAll();
    }

    /**
     * {@code GET  /academic-programs/:id} : get the "id" academicProgram.
     *
     * @param id the id of the academicProgram to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the academicProgram, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/academic-programs/{id}")
    public ResponseEntity<AcademicProgram> getAcademicProgram(@PathVariable Long id) {
        log.debug("REST request to get AcademicProgram : {}", id);
        Optional<AcademicProgram> academicProgram = academicProgramRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(academicProgram);
    }

    /**
     * {@code DELETE  /academic-programs/:id} : delete the "id" academicProgram.
     *
     * @param id the id of the academicProgram to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/academic-programs/{id}")
    public ResponseEntity<Void> deleteAcademicProgram(@PathVariable Long id) {
        log.debug("REST request to delete AcademicProgram : {}", id);
        academicProgramRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
