package com.hung.sneakery.converter;

import com.hung.sneakery.dto.ProductDTO;
import com.hung.sneakery.entity.Product;

import java.util.List;

public interface ProductConverter {

    ProductDTO convertToProductDTO(Product product);

    List<ProductDTO> convertToProductDTOList(List<Product> products);
}
