package com.hung.sneakery.repository;

import com.hung.sneakery.model.Product;
import com.hung.sneakery.model.ProductCategory;
import com.hung.sneakery.repository.custom.ProductCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductCustomRepository {
    Page<Product> findByProductCategory(ProductCategory productCategory, Pageable pageable);
}
