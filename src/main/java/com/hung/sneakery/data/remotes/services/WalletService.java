package com.hung.sneakery.data.remotes.services;

import com.hung.sneakery.data.models.dto.response.BaseResponse;

public interface WalletService {
    BaseResponse createWallet(String userEmail);
}