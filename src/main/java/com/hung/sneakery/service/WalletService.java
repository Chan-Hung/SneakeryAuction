package com.hung.sneakery.service;

import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.entity.Wallet;

public interface WalletService {

    /**
     * Create Wallet
     *
     * @param userEmail String
     * @return BaseResponse
     */
    BaseResponse create(String userEmail);

    /**
     * Get Detailed Wallet
     *
     * @param userId Long
     * @return Wallet
     */
    Wallet getOne(Long userId);
}
