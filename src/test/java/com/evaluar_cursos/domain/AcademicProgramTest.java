package com.evaluar_cursos.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.evaluar_cursos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AcademicProgramTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcademicProgram.class);
        AcademicProgram academicProgram1 = new AcademicProgram();
        academicProgram1.setId(1L);
        AcademicProgram academicProgram2 = new AcademicProgram();
        academicProgram2.setId(academicProgram1.getId());
        assertThat(academicProgram1).isEqualTo(academicProgram2);
        academicProgram2.setId(2L);
        assertThat(academicProgram1).isNotEqualTo(academicProgram2);
        academicProgram1.setId(null);
        assertThat(academicProgram1).isNotEqualTo(academicProgram2);
    }
}
