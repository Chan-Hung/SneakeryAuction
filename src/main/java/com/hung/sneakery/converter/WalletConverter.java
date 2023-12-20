package com.hung.sneakery.converter;

import com.hung.sneakery.dto.WalletDTO;
import com.hung.sneakery.entity.Wallet;

import java.util.List;

public interface WalletConverter {

    WalletDTO convertToWalletDTO(Wallet wallet);

    List<WalletDTO> convertToWalletDTOList(List<Wallet> wallets);
}
