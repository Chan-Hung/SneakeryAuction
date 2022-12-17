package com.hung.sneakery.data.remotes.repositories;

import com.hung.sneakery.data.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    User findByVerificationCode(String verificationCode);
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);


}
