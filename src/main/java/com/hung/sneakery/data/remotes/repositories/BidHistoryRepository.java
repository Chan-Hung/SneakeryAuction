package com.hung.sneakery.data.remotes.repositories;

import com.hung.sneakery.data.models.entities.BidHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;

@Repository()
public interface BidHistoryRepository extends JpaRepository<BidHistory, Long> {
    @Query(value = "SELECT buyer_id as buyerId, max(price) as priceWin from bid_history where product_id = ?1 group by product_id", nativeQuery = true )
    Tuple getWinner(Long productId);
}
