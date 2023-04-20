package com.evaluar_cursos.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.evaluar_cursos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TotalScoreProfesorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TotalScoreProfesor.class);
        TotalScoreProfesor totalScoreProfesor1 = new TotalScoreProfesor();
        totalScoreProfesor1.setId(1L);
        TotalScoreProfesor totalScoreProfesor2 = new TotalScoreProfesor();
        totalScoreProfesor2.setId(totalScoreProfesor1.getId());
        assertThat(totalScoreProfesor1).isEqualTo(totalScoreProfesor2);
        totalScoreProfesor2.setId(2L);
        assertThat(totalScoreProfesor1).isNotEqualTo(totalScoreProfesor2);
        totalScoreProfesor1.setId(null);
        assertThat(totalScoreProfesor1).isNotEqualTo(totalScoreProfesor2);
    }
}
