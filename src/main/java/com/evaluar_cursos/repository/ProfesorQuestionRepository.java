package com.evaluar_cursos.repository;

import com.evaluar_cursos.domain.ProfesorQuestion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProfesorQuestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfesorQuestionRepository extends JpaRepository<ProfesorQuestion, Long> {}
