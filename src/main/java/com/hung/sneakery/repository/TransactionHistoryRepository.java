package com.hung.sneakery.repository;

import com.hung.sneakery.data.models.entities.TransactionHistory;
import com.hung.sneakery.data.models.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
    List<TransactionHistory> findAllByWallet(Wallet wallet);
}
