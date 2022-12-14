package com.hung.sneakery.data.remotes.repositories;

import com.hung.sneakery.data.models.entities.BidHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Optional;

@Repository
public interface BidHistoryRepository extends JpaRepository<BidHistory, Long> {
    @Query(value = "SELECT buyer_id as buyerId, max(price) as priceWin from bid_history where product_id = ?1 group by buyer_id order by priceWin desc limit 1", nativeQuery = true )
    Tuple getWinner(Long productId);

    Optional<BidHistory> findFirstByBidIdOrderByPriceDesc(Long bidId);

    List<BidHistory> findByBid_Id(Long bidId);

    List<BidHistory> findByUser_Id(Long userId);
}
