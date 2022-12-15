package com.hung.sneakery.data.remotes.services;

import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.models.dto.response.DataResponse;
import com.hung.sneakery.data.models.entities.Wallet;

public interface WalletService {
    BaseResponse createWallet(String userEmail);

    DataResponse<Wallet> getOne(Long userId);
}
