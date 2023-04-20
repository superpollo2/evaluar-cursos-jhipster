package com.evaluar_cursos.repository;

import com.evaluar_cursos.domain.Course;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Course entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {}
