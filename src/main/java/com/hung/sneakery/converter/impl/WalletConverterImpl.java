package com.hung.sneakery.converter.impl;

import com.hung.sneakery.converter.WalletConverter;
import com.hung.sneakery.dto.WalletDTO;
import com.hung.sneakery.entity.Wallet;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class WalletConverterImpl implements WalletConverter {

    @Override
    public WalletDTO convertToWalletDTO(Wallet wallet) {
        return WalletDTO.builder()
                .id(wallet.getId())
                .balance(wallet.getBalance())
                .build();
    }

    @Override
    public List<WalletDTO> convertToWalletDTOList(List<Wallet> wallets) {
        return Optional.ofNullable(wallets).orElse(Collections.emptyList())
                .stream().map(this::convertToWalletDTO).collect(Collectors.toList());
    }
}
