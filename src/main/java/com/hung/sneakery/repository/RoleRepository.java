package com.hung.sneakery.repository;

import com.hung.sneakery.model.Role;
import com.hung.sneakery.model.datatype.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
