package com.evaluar_cursos.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.evaluar_cursos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AcademicPeriodTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcademicPeriod.class);
        AcademicPeriod academicPeriod1 = new AcademicPeriod();
        academicPeriod1.setId(1L);
        AcademicPeriod academicPeriod2 = new AcademicPeriod();
        academicPeriod2.setId(academicPeriod1.getId());
        assertThat(academicPeriod1).isEqualTo(academicPeriod2);
        academicPeriod2.setId(2L);
        assertThat(academicPeriod1).isNotEqualTo(academicPeriod2);
        academicPeriod1.setId(null);
        assertThat(academicPeriod1).isNotEqualTo(academicPeriod2);
    }
}
