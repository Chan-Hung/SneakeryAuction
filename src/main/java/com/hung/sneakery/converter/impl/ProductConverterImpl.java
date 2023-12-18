package com.hung.sneakery.converter.impl;

import com.hung.sneakery.converter.ProductConverter;
import com.hung.sneakery.dto.ProductDTO;
import com.hung.sneakery.entity.Product;
import com.hung.sneakery.entity.ProductImage;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductConverterImpl implements ProductConverter {

    @Override
    public ProductDTO convertToProductDTO(Product product) {
        String imagePath = product.getProductImage().stream()
                .filter(image -> BooleanUtils.isTrue(image.getIsThumbnail()))
                .findFirst()
                .map(ProductImage::getPath)
                .orElse(null);

        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .imagePath(imagePath)
                .startPrice(product.getBid().getPriceStart())
                .username(product.getUser().getUsername())
                .bidClosingDate(product.getBid().getBidClosingDateTime())
                .build();
    }

    @Override
    public List<ProductDTO> convertToProductDTOList(List<Product> products) {
        return Optional.ofNullable(products).orElse(Collections.emptyList())
                .stream().map(this::convertToProductDTO).collect(Collectors.toList());
    }
}
