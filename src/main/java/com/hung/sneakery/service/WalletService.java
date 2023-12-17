package com.hung.sneakery.service;

import com.hung.sneakery.dto.WalletDTO;
import com.hung.sneakery.dto.response.BaseResponse;

public interface WalletService {

    /**
     * Create Wallet
     *
     * @param userId String
     * @return BaseResponse
     */
    BaseResponse create(Long userId);

    /**
     * Get Detailed Wallet
     *
     * @param userId Long
     * @return Wallet
     */
    WalletDTO getOne(Long userId);
}
