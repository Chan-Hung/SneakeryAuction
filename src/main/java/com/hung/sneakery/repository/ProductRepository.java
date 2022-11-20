package com.hung.sneakery.repository;

import com.hung.sneakery.model.Category;
import com.hung.sneakery.model.Product;
import com.hung.sneakery.repository.custom.ProductCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductCustomRepository {
    Page<Product> findByCategory(Category category, Pageable pageable);

    @Query(value="SELECT id from products order by id",nativeQuery = true)
    List<Long> getAllId();
}
