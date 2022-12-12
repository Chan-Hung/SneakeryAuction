package com.hung.sneakery.data.remotes.repositories;

import com.hung.sneakery.data.models.entities.Address;
import com.hung.sneakery.data.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser(User user);

    Address findAddressByUser(User user);
}
