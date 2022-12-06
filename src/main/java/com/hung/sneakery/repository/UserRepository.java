package com.hung.sneakery.repository;

import com.hung.sneakery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
