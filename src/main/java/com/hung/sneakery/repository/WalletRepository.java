package com.hung.sneakery.repository;

import com.hung.sneakery.data.models.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findByUser_Id(Long userId);
}
