package com.hung.sneakery.converter.impl;

import com.hung.sneakery.converter.ProductDetailedConverter;
import com.hung.sneakery.dto.ProductDetailedDTO;
import com.hung.sneakery.entity.BidHistory;
import com.hung.sneakery.entity.Media;
import com.hung.sneakery.entity.Product;
import com.hung.sneakery.enums.EBidStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductDetailedConverterImpl implements ProductDetailedConverter {

    @Override
    public ProductDetailedDTO convertToProductDetailedDTO(Product product) {
        List<String> imagePath = product.getImages().stream()
                .map(Media::getPath).collect(Collectors.toList());

        return ProductDetailedDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .startPrice(product.getBid().getPriceStart())
                .imagePath(imagePath)
                .category(product.getCategory().getName())
                .properties(product.getProperties())
                .description(product.getDescription())
                .bidIncrement(product.getBid().getStepBid())
                .currentPrice(getCurrentPrice(product))
                .bidClosingDate(product.getBid().getClosingDateTime())
                .build();
    }

    private Long getCurrentPrice(final Product product) {
        return product.getBid().getBidHistories().stream()
                .filter(bidHistory -> EBidStatus.SUCCESS.equals(bidHistory.getStatus()))
                .map(BidHistory::getActualPrice)
                .max(Long::compareTo)
                .orElse(product.getBid().getPriceStart());
    }
}
