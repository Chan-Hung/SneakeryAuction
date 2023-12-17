package com.hung.sneakery.converter;

import com.hung.sneakery.dto.WalletDTO;
import com.hung.sneakery.entity.Wallet;

public interface WalletConverter {

    WalletDTO convertToWalletDTO(Wallet wallet);
}
