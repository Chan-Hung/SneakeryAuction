package com.hung.sneakery.repository;

import com.hung.sneakery.entities.TransactionHistory;
import com.hung.sneakery.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
    List<TransactionHistory> findAllByWallet(Wallet wallet);
}
