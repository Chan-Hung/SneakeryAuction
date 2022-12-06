package com.hung.sneakery.services;

import com.hung.sneakery.dto.product.ProductDetailedDto;
import com.hung.sneakery.model.datatype.ECondition;
import com.hung.sneakery.payload.response.DataResponse;

import java.util.Map;


public interface ProductService {
    DataResponse<ProductDetailedDto> getProductDetailed(Long id);

    DataResponse<?> getAllProductsId();
    DataResponse<Map<String, Object>> getProductsByCategory(String categoryName, Integer page);
    DataResponse<Map<String,Object>> getProductsHomepage();

    DataResponse<Map<String, Object>> getProductsByFilter(String keyword, String category, ECondition condition, String brand, String color, String size);
}
