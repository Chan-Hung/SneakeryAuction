package com.hung.sneakery.data.remotes.repositories;

import com.hung.sneakery.data.models.entities.ProductDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDescriptionRepository extends JpaRepository<ProductDescription, Long> {
}
