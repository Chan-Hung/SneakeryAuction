package com.hung.sneakery.repository;

import com.hung.sneakery.entities.Order;
import com.hung.sneakery.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByWinner(User user);
}