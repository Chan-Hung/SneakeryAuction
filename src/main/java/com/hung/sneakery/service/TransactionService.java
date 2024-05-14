package com.hung.sneakery.service;

import com.hung.sneakery.dto.TransactionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

    /**
     * Get all Transaction by User
     *
     * @param pageable Pageable
     * @return Page<TransactionDTO>
     */
    Page<TransactionDTO> getAllByUser(Pageable pageable);
}
