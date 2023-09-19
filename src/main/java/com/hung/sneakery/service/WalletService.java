package com.hung.sneakery.service;

import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.dto.response.DataResponse;
import com.hung.sneakery.entity.Wallet;

public interface WalletService {

    BaseResponse createWallet(String userEmail);

    DataResponse<Wallet> getOne(Long userId);
}
