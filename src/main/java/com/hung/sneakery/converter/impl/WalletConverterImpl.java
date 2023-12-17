package com.hung.sneakery.converter.impl;

import com.hung.sneakery.converter.WalletConverter;
import com.hung.sneakery.dto.WalletDTO;
import com.hung.sneakery.entity.Wallet;
import org.springframework.stereotype.Component;

@Component
public class WalletConverterImpl implements WalletConverter {

    @Override
    public WalletDTO convertToWalletDTO(Wallet wallet) {
        return WalletDTO.builder()
                .id(wallet.getId())
                .balance(wallet.getBalance())
                .build();
    }
}
