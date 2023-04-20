package com.evaluar_cursos.repository;

import com.evaluar_cursos.domain.CourseQuestion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CourseQuestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseQuestionRepository extends JpaRepository<CourseQuestion, Long> {}
