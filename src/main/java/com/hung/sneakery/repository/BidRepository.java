package com.hung.sneakery.repository;

import com.hung.sneakery.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    @Query(value="SELECT sum(bids.price_win) from bids WHERE bids.price_win is not null",nativeQuery = true)
    Long getRevenueByAllOrders();
}
