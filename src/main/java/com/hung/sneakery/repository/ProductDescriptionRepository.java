package com.hung.sneakery.repository;

import com.hung.sneakery.model.ProductDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDescriptionRepository extends JpaRepository<ProductDescription, Long> {
}
