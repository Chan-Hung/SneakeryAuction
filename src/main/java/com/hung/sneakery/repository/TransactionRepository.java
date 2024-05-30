package com.hung.sneakery.repository;

import com.hung.sneakery.entity.Transaction;
import com.hung.sneakery.entity.User;
import com.hung.sneakery.enums.EPaymentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findAllByUser(User user, Pageable pageable);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type = ?1")
    Long totalByPaymentType(EPaymentType type);

    @Query("SELECT AVG(t.amount) FROM Transaction t WHERE t.type = ?1")
    Long averageByPaymentType(EPaymentType type);

    @Query("SELECT MAX(t.amount) FROM Transaction t WHERE t.type = ?1")
    Long maxByPaymentType(EPaymentType type);

    @Query("SELECT MIN(t.amount) FROM Transaction t WHERE t.type = ?1")
    Long minByPaymentType(EPaymentType type);
}
