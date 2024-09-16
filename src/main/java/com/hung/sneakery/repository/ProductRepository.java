package com.hung.sneakery.repository;

import com.hung.sneakery.entity.Category;
import com.hung.sneakery.entity.Product;
import com.hung.sneakery.entity.User;
import com.hung.sneakery.enums.BidOutcome;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query(value = "SELECT id from products order by id", nativeQuery = true)
    List<Long> getAllId();

    Page<Product> findAllByBid_BidOutcome(BidOutcome bidOutcome, Pageable pageable);

    List<Product> findByUser(User user);

    Integer countProductByCategory(Category category);

    Page<Product> findByCategoryAndBid_BidOutcome(Category category, BidOutcome bidOutcome, Pageable pageable);
}
