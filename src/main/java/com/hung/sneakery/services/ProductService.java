package com.hung.sneakery.services;

import com.hung.sneakery.dto.product.ProductDetailedDto;
import com.hung.sneakery.model.ProductImage;
import com.hung.sneakery.model.datatype.ECondition;
import com.hung.sneakery.payload.response.DataResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


public interface ProductService {
    DataResponse<ProductDetailedDto> getProductDetailed(Long id);

    DataResponse<?> getAllProductsId();


    DataResponse<Map<String, Object>> getProductsByCategory(String categoryName, Integer page);

    DataResponse<Map<String,Object>> getProductsHomepage();

    DataResponse<Map<String, Object>> getProductsByFilter(String keyword, String category, ECondition condition, String brand, String color, String size);

    DataResponse<ProductDetailedDto> createBiddingProduct(Long categoryId, Long sellerId, String productName, ECondition condition, String brand, String color, Integer size, LocalDateTime bidStartingDate, LocalDateTime bidClosingDate, Long priceStart, Long bidIncrement);

    void insertProductImage(List<ProductImage> productImages, Long productId);
}
