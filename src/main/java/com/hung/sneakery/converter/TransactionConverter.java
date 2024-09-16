package com.hung.sneakery.converter;

import com.hung.sneakery.dto.TransactionDTO;
import com.hung.sneakery.entity.Transaction;

import java.util.List;

public interface TransactionConverter {

    TransactionDTO convertToTransactionDTO(Transaction transaction);

    List<TransactionDTO> convertToTransactionDTOList(List<Transaction> transactions);
}
