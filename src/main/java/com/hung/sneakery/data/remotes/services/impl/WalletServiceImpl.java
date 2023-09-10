package com.hung.sneakery.data.remotes.services.impl;

import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.models.dto.response.DataResponse;
import com.hung.sneakery.data.models.entities.Wallet;
import com.hung.sneakery.data.remotes.repositories.UserRepository;
import com.hung.sneakery.data.remotes.repositories.WalletRepository;
import com.hung.sneakery.data.remotes.services.WalletService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WalletServiceImpl implements WalletService {

    @Resource
    private WalletRepository walletRepository;

    @Resource
    private UserRepository userRepository;

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
