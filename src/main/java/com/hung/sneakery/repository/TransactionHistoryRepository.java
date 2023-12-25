package com.hung.sneakery.repository;

import com.hung.sneakery.entity.TransactionHistory;
import com.hung.sneakery.entity.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

    Page<TransactionHistory> findAllByWallet(Wallet wallet, Pageable pageable);
}
