package com.hung.sneakery.converter.impl;

import com.hung.sneakery.converter.ProductDetailedConverter;
import com.hung.sneakery.dto.ProductDetailedDTO;
import com.hung.sneakery.entity.BidHistory;
import com.hung.sneakery.entity.Product;
import com.hung.sneakery.entity.ProductImage;
import com.hung.sneakery.enums.EBidStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ProductDetailedConverterImpl implements ProductDetailedConverter {

    @Override
    public ProductDetailedDTO convertToProductDetailedDTO(Product product) {
        List<String> imagePath = product.getProductImage().stream()
                .map(ProductImage::getPath).collect(Collectors.toList());

        return ProductDetailedDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .condition(product.getCondition())
                .startPrice(product.getBid().getPriceStart())
                .imagePath(imagePath)
                .category(product.getCategory().getName())
                .brand(product.getBrand())
                .color(product.getColor())
                .size(product.getSize())
                .bidIncrement(product.getBid().getStepBid())
                .currentPrice(getCurrentPrice(product))
                .bidClosingDate(product.getBid().getBidClosingDateTime())
                .build();
    }

    private Long getCurrentPrice(final Product product) {
        Map<Long, BidHistory> bidHistoryMap = product.getBid().getBidHistories().stream()
                .filter(bidHistory -> EBidStatus.SUCCESS.equals(bidHistory.getStatus()))
                .collect(Collectors.toMap(BidHistory::getId, Function.identity()));

        return CollectionUtils.isEmpty(bidHistoryMap) ?
                product.getBid().getPriceStart() :
                bidHistoryMap.get(Collections.max(bidHistoryMap.keySet())).getPrice();
    }
}
