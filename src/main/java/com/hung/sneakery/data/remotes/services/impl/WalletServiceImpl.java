package com.hung.sneakery.data.remotes.services.impl;

import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.models.dto.response.DataResponse;
import com.hung.sneakery.data.models.entities.Wallet;
import com.hung.sneakery.data.remotes.repositories.UserRepository;
import com.hung.sneakery.data.remotes.repositories.WalletRepository;
import com.hung.sneakery.data.remotes.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    WalletRepository walletRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public BaseResponse createWallet(String userEmail) {
        Wallet wallet = new Wallet();
        wallet.setBalance(0L);
        wallet.setUser(userRepository.findByEmail(userEmail));

        walletRepository.save(wallet);

        return new BaseResponse(true, "User's wallet created successfully");
    }

    @Override
    public DataResponse<Wallet> getOne(Long userId) {
        return new DataResponse<>(walletRepository.findByUser_Id(userId));
    }
}
