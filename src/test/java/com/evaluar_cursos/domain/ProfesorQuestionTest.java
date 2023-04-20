package com.evaluar_cursos.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.evaluar_cursos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProfesorQuestionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfesorQuestion.class);
        ProfesorQuestion profesorQuestion1 = new ProfesorQuestion();
        profesorQuestion1.setId(1L);
        ProfesorQuestion profesorQuestion2 = new ProfesorQuestion();
        profesorQuestion2.setId(profesorQuestion1.getId());
        assertThat(profesorQuestion1).isEqualTo(profesorQuestion2);
        profesorQuestion2.setId(2L);
        assertThat(profesorQuestion1).isNotEqualTo(profesorQuestion2);
        profesorQuestion1.setId(null);
        assertThat(profesorQuestion1).isNotEqualTo(profesorQuestion2);
    }
}
