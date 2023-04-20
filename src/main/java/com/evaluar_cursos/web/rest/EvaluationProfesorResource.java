package com.evaluar_cursos.web.rest;

import com.evaluar_cursos.domain.EvaluationProfesor;
import com.evaluar_cursos.repository.EvaluationProfesorRepository;
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
 * REST controller for managing {@link com.evaluar_cursos.domain.EvaluationProfesor}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EvaluationProfesorResource {

    private final Logger log = LoggerFactory.getLogger(EvaluationProfesorResource.class);

    private static final String ENTITY_NAME = "evaluationProfesor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EvaluationProfesorRepository evaluationProfesorRepository;

    public EvaluationProfesorResource(EvaluationProfesorRepository evaluationProfesorRepository) {
        this.evaluationProfesorRepository = evaluationProfesorRepository;
    }

    /**
     * {@code POST  /evaluation-profesors} : Create a new evaluationProfesor.
     *
     * @param evaluationProfesor the evaluationProfesor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new evaluationProfesor, or with status {@code 400 (Bad Request)} if the evaluationProfesor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/evaluation-profesors")
    public ResponseEntity<EvaluationProfesor> createEvaluationProfesor(@RequestBody EvaluationProfesor evaluationProfesor)
        throws URISyntaxException {
        log.debug("REST request to save EvaluationProfesor : {}", evaluationProfesor);
        if (evaluationProfesor.getId() != null) {
            throw new BadRequestAlertException("A new evaluationProfesor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EvaluationProfesor result = evaluationProfesorRepository.save(evaluationProfesor);
        return ResponseEntity
            .created(new URI("/api/evaluation-profesors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /evaluation-profesors/:id} : Updates an existing evaluationProfesor.
     *
     * @param id the id of the evaluationProfesor to save.
     * @param evaluationProfesor the evaluationProfesor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evaluationProfesor,
     * or with status {@code 400 (Bad Request)} if the evaluationProfesor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the evaluationProfesor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/evaluation-profesors/{id}")
    public ResponseEntity<EvaluationProfesor> updateEvaluationProfesor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EvaluationProfesor evaluationProfesor
    ) throws URISyntaxException {
        log.debug("REST request to update EvaluationProfesor : {}, {}", id, evaluationProfesor);
        if (evaluationProfesor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, evaluationProfesor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!evaluationProfesorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EvaluationProfesor result = evaluationProfesorRepository.save(evaluationProfesor);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, evaluationProfesor.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /evaluation-profesors/:id} : Partial updates given fields of an existing evaluationProfesor, field will ignore if it is null
     *
     * @param id the id of the evaluationProfesor to save.
     * @param evaluationProfesor the evaluationProfesor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evaluationProfesor,
     * or with status {@code 400 (Bad Request)} if the evaluationProfesor is not valid,
     * or with status {@code 404 (Not Found)} if the evaluationProfesor is not found,
     * or with status {@code 500 (Internal Server Error)} if the evaluationProfesor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/evaluation-profesors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EvaluationProfesor> partialUpdateEvaluationProfesor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EvaluationProfesor evaluationProfesor
    ) throws URISyntaxException {
        log.debug("REST request to partial update EvaluationProfesor partially : {}, {}", id, evaluationProfesor);
        if (evaluationProfesor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, evaluationProfesor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!evaluationProfesorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EvaluationProfesor> result = evaluationProfesorRepository
            .findById(evaluationProfesor.getId())
            .map(existingEvaluationProfesor -> {
                if (evaluationProfesor.getpScoreQuestionOne() != null) {
                    existingEvaluationProfesor.setpScoreQuestionOne(evaluationProfesor.getpScoreQuestionOne());
                }
                if (evaluationProfesor.getpScoreQuestionTwo() != null) {
                    existingEvaluationProfesor.setpScoreQuestionTwo(evaluationProfesor.getpScoreQuestionTwo());
                }
                if (evaluationProfesor.getpScoreQuestionThree() != null) {
                    existingEvaluationProfesor.setpScoreQuestionThree(evaluationProfesor.getpScoreQuestionThree());
                }
                if (evaluationProfesor.getpScoreQuestionFour() != null) {
                    existingEvaluationProfesor.setpScoreQuestionFour(evaluationProfesor.getpScoreQuestionFour());
                }
                if (evaluationProfesor.getpScoreQuestionFive() != null) {
                    existingEvaluationProfesor.setpScoreQuestionFive(evaluationProfesor.getpScoreQuestionFive());
                }
                if (evaluationProfesor.getpScoreQuestionSix() != null) {
                    existingEvaluationProfesor.setpScoreQuestionSix(evaluationProfesor.getpScoreQuestionSix());
                }

                return existingEvaluationProfesor;
            })
            .map(evaluationProfesorRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, evaluationProfesor.getId().toString())
        );
    }

    /**
     * {@code GET  /evaluation-profesors} : get all the evaluationProfesors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of evaluationProfesors in body.
     */
    @GetMapping("/evaluation-profesors")
    public List<EvaluationProfesor> getAllEvaluationProfesors() {
        log.debug("REST request to get all EvaluationProfesors");
        return evaluationProfesorRepository.findAll();
    }

    /**
     * {@code GET  /evaluation-profesors/:id} : get the "id" evaluationProfesor.
     *
     * @param id the id of the evaluationProfesor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the evaluationProfesor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/evaluation-profesors/{id}")
    public ResponseEntity<EvaluationProfesor> getEvaluationProfesor(@PathVariable Long id) {
        log.debug("REST request to get EvaluationProfesor : {}", id);
        Optional<EvaluationProfesor> evaluationProfesor = evaluationProfesorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(evaluationProfesor);
    }

    /**
     * {@code DELETE  /evaluation-profesors/:id} : delete the "id" evaluationProfesor.
     *
     * @param id the id of the evaluationProfesor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/evaluation-profesors/{id}")
    public ResponseEntity<Void> deleteEvaluationProfesor(@PathVariable Long id) {
        log.debug("REST request to delete EvaluationProfesor : {}", id);
        evaluationProfesorRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
