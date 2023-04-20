package com.evaluar_cursos.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.evaluar_cursos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EvaluationProfesorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvaluationProfesor.class);
        EvaluationProfesor evaluationProfesor1 = new EvaluationProfesor();
        evaluationProfesor1.setId(1L);
        EvaluationProfesor evaluationProfesor2 = new EvaluationProfesor();
        evaluationProfesor2.setId(evaluationProfesor1.getId());
        assertThat(evaluationProfesor1).isEqualTo(evaluationProfesor2);
        evaluationProfesor2.setId(2L);
        assertThat(evaluationProfesor1).isNotEqualTo(evaluationProfesor2);
        evaluationProfesor1.setId(null);
        assertThat(evaluationProfesor1).isNotEqualTo(evaluationProfesor2);
    }
}
