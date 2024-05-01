package com.hung.sneakery.repository;

import com.hung.sneakery.entity.BidHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BidHistoryRepository extends JpaRepository<BidHistory, Long> {

    List<BidHistory> findByBid_IdOrderByCreatedDateDesc(Long bidId);

    List<BidHistory> findByUser_IdOrderByCreatedDateDesc(Long userId);

    Integer countByBid_IdAndCreatedDateAfter(Long bidId, LocalDateTime threeMinutesBeforeBidEnd);
}
