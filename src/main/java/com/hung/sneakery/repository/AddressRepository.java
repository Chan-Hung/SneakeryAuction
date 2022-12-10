package com.hung.sneakery.repository;

import com.hung.sneakery.model.Address;
import com.hung.sneakery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findByUser(User user);
}
