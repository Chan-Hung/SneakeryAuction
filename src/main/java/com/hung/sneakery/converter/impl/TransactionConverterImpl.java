package com.hung.sneakery.converter.impl;

import com.hung.sneakery.converter.BidConverter;
import com.hung.sneakery.converter.TransactionConverter;
import com.hung.sneakery.dto.TransactionDTO;
import com.hung.sneakery.entity.Transaction;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TransactionConverterImpl implements TransactionConverter {

    @Resource
    private BidConverter bidConverter;

    @Override
    public TransactionDTO convertToTransactionDTO(Transaction transaction) {
        return TransactionDTO.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .bid(Objects.nonNull(transaction.getBid()) ? bidConverter.convertToBidDTO(transaction.getBid()) : null)
                .build();
    }

    @Override
    public List<TransactionDTO> convertToTransactionDTOList(List<Transaction> transactions) {
        return Optional.ofNullable(transactions).orElse(Collections.emptyList())
                .stream().map(this::convertToTransactionDTO).collect(Collectors.toList());
    }
}
