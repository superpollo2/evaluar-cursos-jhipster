package com.evaluar_cursos.repository;

import com.evaluar_cursos.domain.TotalScoreCourse;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TotalScoreCourse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TotalScoreCourseRepository extends JpaRepository<TotalScoreCourse, Long> {}
