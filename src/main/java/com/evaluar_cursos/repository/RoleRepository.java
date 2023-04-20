package com.evaluar_cursos.repository;

import com.evaluar_cursos.domain.Role;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Role entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {}
