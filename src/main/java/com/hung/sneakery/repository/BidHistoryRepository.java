package com.hung.sneakery.repository;

import com.hung.sneakery.model.BidHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidHistoryRepository extends JpaRepository<BidHistory, Long> {
}
