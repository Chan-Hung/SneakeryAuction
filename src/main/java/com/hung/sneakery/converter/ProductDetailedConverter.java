package com.hung.sneakery.converter;

import com.hung.sneakery.dto.ProductDetailedDTO;
import com.hung.sneakery.entity.Product;

public interface ProductDetailedConverter {

    ProductDetailedDTO convertToProductDetailedDTO(Product product);
}
