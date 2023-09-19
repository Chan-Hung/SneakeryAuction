package com.hung.sneakery.service;

import com.hung.sneakery.dto.ProductDetailedDTO;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.enums.ECondition;
import com.hung.sneakery.enums.ESorting;
import com.hung.sneakery.dto.response.DataResponse;

import java.util.List;
import java.util.Map;


public interface ProductService {

    DataResponse<ProductDetailedDTO> getOne(Long id);

    DataResponse<?> getAllProductsId();

    DataResponse<Map<String, Object>> getProductsByCategory(String categoryName, Integer page);

    DataResponse<Map<String,Object>> getProductsHomepage();

    DataResponse<Map<String, Object>> getProductsByFilter(String keyword, String category, ECondition condition, List<String> brands, List<String> colors, List<Integer> sizes, Long priceStart, Long priceEnd, ESorting sorting);

    BaseResponse deleteProduct(Long id);
}
