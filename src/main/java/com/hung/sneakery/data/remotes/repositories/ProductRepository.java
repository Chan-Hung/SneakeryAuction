package com.hung.sneakery.data.remotes.repositories;

import com.hung.sneakery.data.models.entities.Category;
import com.hung.sneakery.data.models.entities.Product;
import com.hung.sneakery.data.remotes.repositories.custom.ProductCustomRepository;
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
