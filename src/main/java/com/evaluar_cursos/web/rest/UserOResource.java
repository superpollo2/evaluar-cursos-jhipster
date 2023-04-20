package com.evaluar_cursos.web.rest;

import com.evaluar_cursos.domain.UserO;
import com.evaluar_cursos.repository.UserORepository;
import com.evaluar_cursos.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.evaluar_cursos.domain.UserO}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UserOResource {

    private final Logger log = LoggerFactory.getLogger(UserOResource.class);

    private static final String ENTITY_NAME = "userO";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserORepository userORepository;

    public UserOResource(UserORepository userORepository) {
        this.userORepository = userORepository;
    }

    /**
     * {@code POST  /user-os} : Create a new userO.
     *
     * @param userO the userO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userO, or with status {@code 400 (Bad Request)} if the userO has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-os")
    public ResponseEntity<UserO> createUserO(@Valid @RequestBody UserO userO) throws URISyntaxException {
        log.debug("REST request to save UserO : {}", userO);
        if (userO.getId() != null) {
            throw new BadRequestAlertException("A new userO cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserO result = userORepository.save(userO);
        return ResponseEntity
            .created(new URI("/api/user-os/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-os/:id} : Updates an existing userO.
     *
     * @param id the id of the userO to save.
     * @param userO the userO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userO,
     * or with status {@code 400 (Bad Request)} if the userO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-os/{id}")
    public ResponseEntity<UserO> updateUserO(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody UserO userO)
        throws URISyntaxException {
        log.debug("REST request to update UserO : {}, {}", id, userO);
        if (userO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userORepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserO result = userORepository.save(userO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-os/:id} : Partial updates given fields of an existing userO, field will ignore if it is null
     *
     * @param id the id of the userO to save.
     * @param userO the userO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userO,
     * or with status {@code 400 (Bad Request)} if the userO is not valid,
     * or with status {@code 404 (Not Found)} if the userO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-os/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserO> partialUpdateUserO(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserO userO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserO partially : {}, {}", id, userO);
        if (userO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userORepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserO> result = userORepository
            .findById(userO.getId())
            .map(existingUserO -> {
                if (userO.getUserName() != null) {
                    existingUserO.setUserName(userO.getUserName());
                }
                if (userO.getPassword() != null) {
                    existingUserO.setPassword(userO.getPassword());
                }
                if (userO.getEmail() != null) {
                    existingUserO.setEmail(userO.getEmail());
                }

                return existingUserO;
            })
            .map(userORepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-os} : get all the userOS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userOS in body.
     */
    @GetMapping("/user-os")
    public List<UserO> getAllUserOS() {
        log.debug("REST request to get all UserOS");
        return userORepository.findAll();
    }

    /**
     * {@code GET  /user-os/:id} : get the "id" userO.
     *
     * @param id the id of the userO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-os/{id}")
    public ResponseEntity<UserO> getUserO(@PathVariable Long id) {
        log.debug("REST request to get UserO : {}", id);
        Optional<UserO> userO = userORepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userO);
    }

    /**
     * {@code DELETE  /user-os/:id} : delete the "id" userO.
     *
     * @param id the id of the userO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-os/{id}")
    public ResponseEntity<Void> deleteUserO(@PathVariable Long id) {
        log.debug("REST request to delete UserO : {}", id);
        userORepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
