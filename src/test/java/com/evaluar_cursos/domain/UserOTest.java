package com.evaluar_cursos.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.evaluar_cursos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserOTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserO.class);
        UserO userO1 = new UserO();
        userO1.setId(1L);
        UserO userO2 = new UserO();
        userO2.setId(userO1.getId());
        assertThat(userO1).isEqualTo(userO2);
        userO2.setId(2L);
        assertThat(userO1).isNotEqualTo(userO2);
        userO1.setId(null);
        assertThat(userO1).isNotEqualTo(userO2);
    }
}
