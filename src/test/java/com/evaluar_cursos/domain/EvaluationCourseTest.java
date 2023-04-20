package com.evaluar_cursos.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.evaluar_cursos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EvaluationCourseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvaluationCourse.class);
        EvaluationCourse evaluationCourse1 = new EvaluationCourse();
        evaluationCourse1.setId(1L);
        EvaluationCourse evaluationCourse2 = new EvaluationCourse();
        evaluationCourse2.setId(evaluationCourse1.getId());
        assertThat(evaluationCourse1).isEqualTo(evaluationCourse2);
        evaluationCourse2.setId(2L);
        assertThat(evaluationCourse1).isNotEqualTo(evaluationCourse2);
        evaluationCourse1.setId(null);
        assertThat(evaluationCourse1).isNotEqualTo(evaluationCourse2);
    }
}
