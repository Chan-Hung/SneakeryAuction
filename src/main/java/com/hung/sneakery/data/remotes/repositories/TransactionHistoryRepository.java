package com.hung.sneakery.data.remotes.repositories;

import com.hung.sneakery.data.models.entities.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
}
