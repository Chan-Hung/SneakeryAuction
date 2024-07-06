package com.hung.sneakery.converter.impl;

import com.hung.sneakery.converter.ProductConverter;
import com.hung.sneakery.dto.ProductDTO;
import com.hung.sneakery.entity.BidHistory;
import com.hung.sneakery.entity.Media;
import com.hung.sneakery.entity.Product;
import com.hung.sneakery.enums.BidOutcome;
import com.hung.sneakery.enums.EBidStatus;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductConverterImpl implements ProductConverter {

    @Override
    public ProductDTO convertToProductDTO(Product product) {
        if (product.getBid().getBidOutcome().equals(BidOutcome.CLOSED) || product.getBid().getBidOutcome().equals(BidOutcome.CLOSED_WITHOUT_WINNER)) {
            return null;
        }
        String imagePath = product.getImages().stream()
                .filter(image -> BooleanUtils.isTrue(image.getIsThumbnail()))
                .findFirst()
                .map(Media::getPath)
                .orElse(StringUtils.EMPTY);

        Long currentPrice = product.getBid().getBidHistories().stream()
                .filter(bidHistory -> EBidStatus.SUCCESS.equals(bidHistory.getStatus()))
                .map(BidHistory::getActualPrice)
                .max(Long::compareTo)
                .orElse(product.getBid().getPriceStart());

        String holder = Objects.nonNull(product.getBid().getHolder()) ? product.getBid().getHolder().getUsername() : null;

        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .imagePath(imagePath)
                .currentPrice(currentPrice)
                .holder(holder)
                .bidCreatedDate(product.getBid().getCreatedDate())
                .bidClosingDate(product.getBid().getClosingDateTime())
                .numberOfBids(product.getBid().getNumberOfBids())
                .build();
    }

    @Override
    public List<ProductDTO> convertToProductDTOList(List<Product> products) {
        return Optional.ofNullable(products).orElse(Collections.emptyList())
                .stream().map(this::convertToProductDTO).collect(Collectors.toList());
    }
}
