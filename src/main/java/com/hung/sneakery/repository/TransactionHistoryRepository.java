package com.hung.sneakery.repository;

import com.hung.sneakery.entity.TransactionHistory;
import com.hung.sneakery.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

    List<TransactionHistory> findAllByWallet(Wallet wallet);
}
