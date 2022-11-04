package com.hung.sneakery.repository;

import com.hung.sneakery.model.Product;
import com.hung.sneakery.model.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//    @Query(value = "SELECT u FROM Product u JOIN ProductCategory ct where u.productCategory.id = ct.id and ct.categoryName = ?1")
    Page<Product> findByProductCategory(ProductCategory productCategory, Pageable pageable);
}
