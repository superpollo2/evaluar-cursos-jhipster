package com.evaluar_cursos.repository;

import com.evaluar_cursos.domain.UserO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserO entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserORepository extends JpaRepository<UserO, String> {}
