package com.hung.sneakery.service;

import com.hung.sneakery.dto.ProductDetailedDTO;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.enums.ECondition;
import com.hung.sneakery.enums.ESorting;

import java.util.List;
import java.util.Map;


public interface ProductService {

    /**
     * Get One Detailed Product
     *
     * @param id Long
     * @return ProductDetailedDTO
     */
    ProductDetailedDTO getOne(Long id);

    /**
     * Get All Id Of Products
     *
     * @return List<Long>
     */
    List<Long> getAllProductsId();

    /**
     * Get Products By Category
     *
     * @param categoryName String
     * @param page         Integer
     * @return Map<String, Object>
     */
    Map<String, Object> getProductsByCategory(String categoryName, Integer page);

    /**
     * Get Products Showed On Homepage
     *
     * @return Map<String, Object>
     */
    Map<String, Object> getProductsHomepage();

    /**
     * Get Products By Filter
     *
     * @param keyword    String
     * @param category   String
     * @param condition  ECondition
     * @param brands     List<String>
     * @param colors     List<String>
     * @param sizes      List<Integer>
     * @param priceStart Long
     * @param priceEnd   Long
     * @param sorting    ESorting
     * @return Map<String, Object>
     */
    Map<String, Object> getProductsByFilter(String keyword, String category, ECondition condition,
                                            List<String> brands, List<String> colors, List<Integer> sizes, Long priceStart, Long priceEnd, ESorting sorting);

    /**
     * Delete Product
     *
     * @param id Long
     * @return BaseResponse
     */
    BaseResponse delete(Long id);
}
