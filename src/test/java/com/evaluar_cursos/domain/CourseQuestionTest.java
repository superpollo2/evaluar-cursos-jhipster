package com.evaluar_cursos.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.evaluar_cursos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CourseQuestionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseQuestion.class);
        CourseQuestion courseQuestion1 = new CourseQuestion();
        courseQuestion1.setId(1L);
        CourseQuestion courseQuestion2 = new CourseQuestion();
        courseQuestion2.setId(courseQuestion1.getId());
        assertThat(courseQuestion1).isEqualTo(courseQuestion2);
        courseQuestion2.setId(2L);
        assertThat(courseQuestion1).isNotEqualTo(courseQuestion2);
        courseQuestion1.setId(null);
        assertThat(courseQuestion1).isNotEqualTo(courseQuestion2);
    }
}
