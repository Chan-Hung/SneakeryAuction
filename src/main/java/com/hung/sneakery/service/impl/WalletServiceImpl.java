package com.hung.sneakery.service.impl;

import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.entity.User;
import com.hung.sneakery.entity.Wallet;
import com.hung.sneakery.repository.UserRepository;
import com.hung.sneakery.repository.WalletRepository;
import com.hung.sneakery.service.WalletService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WalletServiceImpl implements WalletService {

    @Resource
    private WalletRepository walletRepository;

    @Resource
    private UserRepository userRepository;

    @Override
    public BaseResponse create(final Long id) {
        Wallet wallet = new Wallet();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        wallet.setBalance(0L);
        wallet.setUser(user);
        walletRepository.save(wallet);

        return new BaseResponse(true, "User's wallet created successfully");
    }

    @Override
    public Wallet getOne(final Long userId) {
        return walletRepository.findByUser_Id(userId);
    }
}
