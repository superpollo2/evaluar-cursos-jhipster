package com.evaluar_cursos.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.evaluar_cursos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EnrollCourseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnrollCourse.class);
        EnrollCourse enrollCourse1 = new EnrollCourse();
        enrollCourse1.setId(1L);
        EnrollCourse enrollCourse2 = new EnrollCourse();
        enrollCourse2.setId(enrollCourse1.getId());
        assertThat(enrollCourse1).isEqualTo(enrollCourse2);
        enrollCourse2.setId(2L);
        assertThat(enrollCourse1).isNotEqualTo(enrollCourse2);
        enrollCourse1.setId(null);
        assertThat(enrollCourse1).isNotEqualTo(enrollCourse2);
    }
}
