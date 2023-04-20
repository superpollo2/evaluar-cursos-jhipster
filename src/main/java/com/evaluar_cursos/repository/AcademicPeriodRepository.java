package com.evaluar_cursos.repository;

import com.evaluar_cursos.domain.AcademicPeriod;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AcademicPeriod entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcademicPeriodRepository extends JpaRepository<AcademicPeriod, Long> {}
