package com.evaluar_cursos.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.evaluar_cursos.IntegrationTest;
import com.evaluar_cursos.domain.UserO;
import com.evaluar_cursos.repository.UserORepository;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link UserOResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserOResourceIT {

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/user-os";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{userName}";

    @Autowired
    private UserORepository userORepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserOMockMvc;

    private UserO userO;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserO createEntity(EntityManager em) {
        UserO userO = new UserO().password(DEFAULT_PASSWORD).email(DEFAULT_EMAIL);
        return userO;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserO createUpdatedEntity(EntityManager em) {
        UserO userO = new UserO().password(UPDATED_PASSWORD).email(UPDATED_EMAIL);
        return userO;
    }

    @BeforeEach
    public void initTest() {
        userO = createEntity(em);
    }

    @Test
    @Transactional
    void createUserO() throws Exception {
        int databaseSizeBeforeCreate = userORepository.findAll().size();
        // Create the UserO
        restUserOMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userO)))
            .andExpect(status().isCreated());

        // Validate the UserO in the database
        List<UserO> userOList = userORepository.findAll();
        assertThat(userOList).hasSize(databaseSizeBeforeCreate + 1);
        UserO testUserO = userOList.get(userOList.size() - 1);
        assertThat(testUserO.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testUserO.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void createUserOWithExistingId() throws Exception {
        // Create the UserO with an existing ID
        userO.setUserName("existing_id");

        int databaseSizeBeforeCreate = userORepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserOMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userO)))
            .andExpect(status().isBadRequest());

        // Validate the UserO in the database
        List<UserO> userOList = userORepository.findAll();
        assertThat(userOList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserOS() throws Exception {
        // Initialize the database
        userO.setUserName(UUID.randomUUID().toString());
        userORepository.saveAndFlush(userO);

        // Get all the userOList
        restUserOMockMvc
            .perform(get(ENTITY_API_URL + "?sort=userName,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(userO.getUserName())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    void getUserO() throws Exception {
        // Initialize the database
        userO.setUserName(UUID.randomUUID().toString());
        userORepository.saveAndFlush(userO);

        // Get the userO
        restUserOMockMvc
            .perform(get(ENTITY_API_URL_ID, userO.getUserName()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.userName").value(userO.getUserName()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    void getNonExistingUserO() throws Exception {
        // Get the userO
        restUserOMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserO() throws Exception {
        // Initialize the database
        userO.setUserName(UUID.randomUUID().toString());
        userORepository.saveAndFlush(userO);

        int databaseSizeBeforeUpdate = userORepository.findAll().size();

        // Update the userO
        UserO updatedUserO = userORepository.findById(userO.getUserName()).get();
        // Disconnect from session so that the updates on updatedUserO are not directly saved in db
        em.detach(updatedUserO);
        updatedUserO.password(UPDATED_PASSWORD).email(UPDATED_EMAIL);

        restUserOMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserO.getUserName())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserO))
            )
            .andExpect(status().isOk());

        // Validate the UserO in the database
        List<UserO> userOList = userORepository.findAll();
        assertThat(userOList).hasSize(databaseSizeBeforeUpdate);
        UserO testUserO = userOList.get(userOList.size() - 1);
        assertThat(testUserO.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUserO.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void putNonExistingUserO() throws Exception {
        int databaseSizeBeforeUpdate = userORepository.findAll().size();
        userO.setUserName(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserOMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userO.getUserName())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserO in the database
        List<UserO> userOList = userORepository.findAll();
        assertThat(userOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserO() throws Exception {
        int databaseSizeBeforeUpdate = userORepository.findAll().size();
        userO.setUserName(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserOMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserO in the database
        List<UserO> userOList = userORepository.findAll();
        assertThat(userOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserO() throws Exception {
        int databaseSizeBeforeUpdate = userORepository.findAll().size();
        userO.setUserName(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserOMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserO in the database
        List<UserO> userOList = userORepository.findAll();
        assertThat(userOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserOWithPatch() throws Exception {
        // Initialize the database
        userO.setUserName(UUID.randomUUID().toString());
        userORepository.saveAndFlush(userO);

        int databaseSizeBeforeUpdate = userORepository.findAll().size();

        // Update the userO using partial update
        UserO partialUpdatedUserO = new UserO();
        partialUpdatedUserO.setUserName(userO.getUserName());

        partialUpdatedUserO.password(UPDATED_PASSWORD);

        restUserOMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserO.getUserName())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserO))
            )
            .andExpect(status().isOk());

        // Validate the UserO in the database
        List<UserO> userOList = userORepository.findAll();
        assertThat(userOList).hasSize(databaseSizeBeforeUpdate);
        UserO testUserO = userOList.get(userOList.size() - 1);
        assertThat(testUserO.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUserO.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void fullUpdateUserOWithPatch() throws Exception {
        // Initialize the database
        userO.setUserName(UUID.randomUUID().toString());
        userORepository.saveAndFlush(userO);

        int databaseSizeBeforeUpdate = userORepository.findAll().size();

        // Update the userO using partial update
        UserO partialUpdatedUserO = new UserO();
        partialUpdatedUserO.setUserName(userO.getUserName());

        partialUpdatedUserO.password(UPDATED_PASSWORD).email(UPDATED_EMAIL);

        restUserOMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserO.getUserName())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserO))
            )
            .andExpect(status().isOk());

        // Validate the UserO in the database
        List<UserO> userOList = userORepository.findAll();
        assertThat(userOList).hasSize(databaseSizeBeforeUpdate);
        UserO testUserO = userOList.get(userOList.size() - 1);
        assertThat(testUserO.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUserO.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void patchNonExistingUserO() throws Exception {
        int databaseSizeBeforeUpdate = userORepository.findAll().size();
        userO.setUserName(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserOMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userO.getUserName())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserO in the database
        List<UserO> userOList = userORepository.findAll();
        assertThat(userOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserO() throws Exception {
        int databaseSizeBeforeUpdate = userORepository.findAll().size();
        userO.setUserName(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserOMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserO in the database
        List<UserO> userOList = userORepository.findAll();
        assertThat(userOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserO() throws Exception {
        int databaseSizeBeforeUpdate = userORepository.findAll().size();
        userO.setUserName(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserOMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserO in the database
        List<UserO> userOList = userORepository.findAll();
        assertThat(userOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserO() throws Exception {
        // Initialize the database
        userO.setUserName(UUID.randomUUID().toString());
        userORepository.saveAndFlush(userO);

        int databaseSizeBeforeDelete = userORepository.findAll().size();

        // Delete the userO
        restUserOMockMvc
            .perform(delete(ENTITY_API_URL_ID, userO.getUserName()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserO> userOList = userORepository.findAll();
        assertThat(userOList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
