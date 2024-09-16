package com.hung.sneakery.service.impl;

import com.hung.sneakery.converter.TransactionConverter;
import com.hung.sneakery.dto.TransactionDTO;
import com.hung.sneakery.entity.Transaction;
import com.hung.sneakery.entity.User;
import com.hung.sneakery.repository.TransactionRepository;
import com.hung.sneakery.service.TransactionService;
import com.hung.sneakery.utils.SneakeryUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Resource
    private TransactionRepository transactionRepository;

    @Resource
    private TransactionConverter transactionConverter;

    @Resource
    private SneakeryUtil sneakeryUtil;

    @Override
    public Page<TransactionDTO> getAllByUser(Pageable pageable) {
        User user = sneakeryUtil.getCurrentUser();
        Page<Transaction> transactionPage = transactionRepository.findAllByUserAndBidIsNotNull(user, pageable);
        List<TransactionDTO> transactionDTOs = transactionConverter.convertToTransactionDTOList(transactionPage.getContent());
        return new PageImpl<>(transactionDTOs, pageable, transactionPage.getTotalElements());
    }
}
