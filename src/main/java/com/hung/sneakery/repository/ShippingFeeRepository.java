package com.hung.sneakery.repository;

import com.hung.sneakery.entities.ShippingFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingFeeRepository extends JpaRepository<ShippingFee, Long> {
    ShippingFee findShippingFeeByOriginAndDestination(String origin, String destination);
}
