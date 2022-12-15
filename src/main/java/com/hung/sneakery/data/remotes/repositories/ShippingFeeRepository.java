package com.hung.sneakery.data.remotes.repositories;

import com.hung.sneakery.data.models.entities.ShippingFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingFeeRepository extends JpaRepository<ShippingFee, Long> {
    ShippingFee findShippingFeeByOriginAndDestination(String origin, String destination);
}
