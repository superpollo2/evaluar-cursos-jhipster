package com.evaluar_cursos.repository;

import com.evaluar_cursos.domain.TotalScoreProfesor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TotalScoreProfesor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TotalScoreProfesorRepository extends JpaRepository<TotalScoreProfesor, Long> {}
