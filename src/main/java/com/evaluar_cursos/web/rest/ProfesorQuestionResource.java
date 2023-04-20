package com.evaluar_cursos.web.rest;

import com.evaluar_cursos.domain.ProfesorQuestion;
import com.evaluar_cursos.repository.ProfesorQuestionRepository;
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
 * REST controller for managing {@link com.evaluar_cursos.domain.ProfesorQuestion}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProfesorQuestionResource {

    private final Logger log = LoggerFactory.getLogger(ProfesorQuestionResource.class);

    private static final String ENTITY_NAME = "profesorQuestion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfesorQuestionRepository profesorQuestionRepository;

    public ProfesorQuestionResource(ProfesorQuestionRepository profesorQuestionRepository) {
        this.profesorQuestionRepository = profesorQuestionRepository;
    }

    /**
     * {@code POST  /profesor-questions} : Create a new profesorQuestion.
     *
     * @param profesorQuestion the profesorQuestion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new profesorQuestion, or with status {@code 400 (Bad Request)} if the profesorQuestion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/profesor-questions")
    public ResponseEntity<ProfesorQuestion> createProfesorQuestion(@RequestBody ProfesorQuestion profesorQuestion)
        throws URISyntaxException {
        log.debug("REST request to save ProfesorQuestion : {}", profesorQuestion);
        if (profesorQuestion.getId() != null) {
            throw new BadRequestAlertException("A new profesorQuestion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfesorQuestion result = profesorQuestionRepository.save(profesorQuestion);
        return ResponseEntity
            .created(new URI("/api/profesor-questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /profesor-questions/:id} : Updates an existing profesorQuestion.
     *
     * @param id the id of the profesorQuestion to save.
     * @param profesorQuestion the profesorQuestion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profesorQuestion,
     * or with status {@code 400 (Bad Request)} if the profesorQuestion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the profesorQuestion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/profesor-questions/{id}")
    public ResponseEntity<ProfesorQuestion> updateProfesorQuestion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProfesorQuestion profesorQuestion
    ) throws URISyntaxException {
        log.debug("REST request to update ProfesorQuestion : {}, {}", id, profesorQuestion);
        if (profesorQuestion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, profesorQuestion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!profesorQuestionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProfesorQuestion result = profesorQuestionRepository.save(profesorQuestion);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, profesorQuestion.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /profesor-questions/:id} : Partial updates given fields of an existing profesorQuestion, field will ignore if it is null
     *
     * @param id the id of the profesorQuestion to save.
     * @param profesorQuestion the profesorQuestion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profesorQuestion,
     * or with status {@code 400 (Bad Request)} if the profesorQuestion is not valid,
     * or with status {@code 404 (Not Found)} if the profesorQuestion is not found,
     * or with status {@code 500 (Internal Server Error)} if the profesorQuestion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/profesor-questions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProfesorQuestion> partialUpdateProfesorQuestion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProfesorQuestion profesorQuestion
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProfesorQuestion partially : {}, {}", id, profesorQuestion);
        if (profesorQuestion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, profesorQuestion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!profesorQuestionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProfesorQuestion> result = profesorQuestionRepository
            .findById(profesorQuestion.getId())
            .map(existingProfesorQuestion -> {
                if (profesorQuestion.getProfesorQuestion() != null) {
                    existingProfesorQuestion.setProfesorQuestion(profesorQuestion.getProfesorQuestion());
                }

                return existingProfesorQuestion;
            })
            .map(profesorQuestionRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, profesorQuestion.getId().toString())
        );
    }

    /**
     * {@code GET  /profesor-questions} : get all the profesorQuestions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of profesorQuestions in body.
     */
    @GetMapping("/profesor-questions")
    public List<ProfesorQuestion> getAllProfesorQuestions() {
        log.debug("REST request to get all ProfesorQuestions");
        return profesorQuestionRepository.findAll();
    }

    /**
     * {@code GET  /profesor-questions/:id} : get the "id" profesorQuestion.
     *
     * @param id the id of the profesorQuestion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the profesorQuestion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/profesor-questions/{id}")
    public ResponseEntity<ProfesorQuestion> getProfesorQuestion(@PathVariable Long id) {
        log.debug("REST request to get ProfesorQuestion : {}", id);
        Optional<ProfesorQuestion> profesorQuestion = profesorQuestionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(profesorQuestion);
    }

    /**
     * {@code DELETE  /profesor-questions/:id} : delete the "id" profesorQuestion.
     *
     * @param id the id of the profesorQuestion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/profesor-questions/{id}")
    public ResponseEntity<Void> deleteProfesorQuestion(@PathVariable Long id) {
        log.debug("REST request to delete ProfesorQuestion : {}", id);
        profesorQuestionRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
