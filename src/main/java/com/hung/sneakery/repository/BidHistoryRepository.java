package com.hung.sneakery.repository;

import com.hung.sneakery.entity.BidHistory;
import com.hung.sneakery.enums.EBidStatus;
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

    Optional<BidHistory> findFirstByBidIdAndStatusOrderByPriceDesc(Long bidId, EBidStatus status);

    List<BidHistory> findByBid_IdOrderByCreatedDateDesc(Long bidId);

    List<BidHistory> findByUser_Id(Long userId);
}
