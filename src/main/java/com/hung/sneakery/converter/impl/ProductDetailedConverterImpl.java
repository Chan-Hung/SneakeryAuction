package com.hung.sneakery.converter.impl;

import com.hung.sneakery.converter.ProductDetailedConverter;
import com.hung.sneakery.converter.UserConverter;
import com.hung.sneakery.dto.ProductDetailedDTO;
import com.hung.sneakery.entity.Bid;
import com.hung.sneakery.entity.BidHistory;
import com.hung.sneakery.entity.Media;
import com.hung.sneakery.entity.Product;
import com.hung.sneakery.enums.EBidStatus;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ProductDetailedConverterImpl implements ProductDetailedConverter {

    @Resource
    private UserConverter userConverter;

    @Override
    public ProductDetailedDTO convertToProductDetailedDTO(Product product) {
        List<String> imagePath = product.getImages()
                .stream()
                .map(Media::getPath)
                .collect(Collectors.toList());

        Bid bid = product.getBid();

        return ProductDetailedDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .startPrice(bid.getPriceStart())
                .imagePath(imagePath)
                .category(product.getCategory().getName())
                .properties(product.getProperties())
                .description(product.getDescription())
                .bidIncrement(bid.getStepBid())
                .currentPrice(getCurrentPrice(product))
                .holder(Objects.nonNull(bid.getHolder()) ? bid.getHolder().getUsername() : null)
                .seller(userConverter.convertToUserDTO(product.getUser()))
                .bidCreatedDate(bid.getCreatedDate())
                .bidClosingDate(bid.getClosingDateTime())
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
