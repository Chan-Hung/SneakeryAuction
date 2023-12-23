package com.hung.sneakery.repository;

import com.hung.sneakery.entity.Order;
import com.hung.sneakery.entity.User;
import com.hung.sneakery.enums.EOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByWinnerAndStatus(User user, EOrderStatus status, Pageable pageable);
}
