package com.hung.sneakery.repository;

import com.hung.sneakery.entity.Product;
import com.hung.sneakery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query(value = "SELECT id from products order by id", nativeQuery = true)
    List<Long> getAllId();

    List<Product> findByUser(User user);
}
