package com.hung.sneakery.service;

import com.hung.sneakery.dto.ProductDTO;
import com.hung.sneakery.dto.ProductDetailedDTO;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.enums.ECondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


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
     * Get Products Showed On Homepage
     *
     * @param pageable Pageable
     * @return Page<ProductDTO>
     */
    Page<ProductDTO> getProductsHomepage(Pageable pageable);

    /**
     * Get Products By Category
     *
     * @param pageable     Pageable
     * @param categoryName String
     * @return Page<ProductDTO>
     */
    Page<ProductDTO> getProductsByCategory(Pageable pageable, String categoryName);

    /**
     * Get Products By Filter
     *
     * @param pageable   Pageable
     * @param category   String
     * @param condition  ECondition
     * @param brands     List<String>
     * @param colors     List<String>
     * @param sizes      List<Integer>
     * @param priceStart Long
     * @param priceEnd   Long
     * @return Page<ProductDTO>
     */
    Page<ProductDTO> getProductsByFilter(Pageable pageable, String category, ECondition condition,
                                         List<String> brands, List<String> colors,
                                         List<Integer> sizes, Long priceStart, Long priceEnd);

    /**
     * Get Products By Search
     *
     * @param pageable    Pageable
     * @param productName String
     * @return Page<ProductDTO>
     */
    Page<ProductDTO> getProductsBySearch(Pageable pageable, String productName);

    /**
     * Delete Product
     *
     * @param id Long
     * @return BaseResponse
     */
    BaseResponse delete(Long id);
}
