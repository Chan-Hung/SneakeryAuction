package com.hung.sneakery.converter.impl;

import com.hung.sneakery.converter.BidConverter;
import com.hung.sneakery.converter.ProductConverter;
import com.hung.sneakery.dto.BidDTO;
import com.hung.sneakery.dto.BidDetailDTO;
import com.hung.sneakery.dto.WinnerDTO;
import com.hung.sneakery.entity.Bid;
import com.hung.sneakery.entity.User;
import com.hung.sneakery.enums.PaymentStatus;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BidConverterImpl implements BidConverter {

    @Resource
    private ProductConverter productConverter;

    @Override
    public BidDTO convertToBidDTO(Bid bid) {
        WinnerDTO winnerDTO = null;
        if (bid.getSellerPaymentStatus() == PaymentStatus.COMPLETED) {
            User winner = bid.getHolder();
            winnerDTO = WinnerDTO.builder()
                    .id(winner.getId())
                    .username(winner.getUsername())
                    .email(winner.getEmail())
                    .phoneNumber(winner.getPhoneNumber())
                    .build();
        }

        return BidDTO.builder()
                .bidId(bid.getId())
                .priceWin(bid.getPriceWin())
                .stepBid(bid.getStepBid())
                .priceStart(bid.getPriceStart())
                .bidStartingDate(bid.getCreatedDate())
                .bidOutCome(bid.getBidOutcome())
                .sellerPaymentStatus(bid.getSellerPaymentStatus())
                .winner(winnerDTO)
                .product(productConverter.convertToProductDTO(bid.getProduct()))
                .build();
    }

    @Override
    public BidDetailDTO convertToBidDetailDTO(Bid bid) {
        return BidDetailDTO.builder()
                .bidId(bid.getId())
                .priceWin(bid.getPriceWin())
                .stepBid(bid.getStepBid())
                .priceStart(bid.getPriceStart())
                .bidStartingDate(bid.getCreatedDate())
                .build();
    }

    @Override
    public List<BidDTO> convertToBidDTOList(List<Bid> bids) {
        return Optional.ofNullable(bids).orElse(Collections.emptyList())
                .stream().map(this::convertToBidDTO).collect(Collectors.toList());
    }
}
