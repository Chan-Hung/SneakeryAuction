package com.hung.sneakery.converter.impl;

import com.hung.sneakery.converter.BidConverter;
import com.hung.sneakery.converter.ProductConverter;
import com.hung.sneakery.dto.BidDTO;
import com.hung.sneakery.entity.Bid;
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
        return BidDTO.builder()
                .bidId(bid.getId())
                .priceWin(bid.getPriceWin())
                .stepBid(bid.getStepBid())
                .priceStart(bid.getPriceStart())
                .bidStartingDate(bid.getCreatedDate())
                .product(productConverter.convertToProductDTO(null))
                .build();
    }

    @Override
    public List<BidDTO> convertToBidDTOList(List<Bid> bids) {
        return Optional.ofNullable(bids).orElse(Collections.emptyList())
                .stream().map(this::convertToBidDTO).collect(Collectors.toList());
    }
}
