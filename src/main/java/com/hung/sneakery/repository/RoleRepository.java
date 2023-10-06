package com.hung.sneakery.repository;

import com.hung.sneakery.data.models.entities.Role;
import com.hung.sneakery.utils.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(ERole name);
}
