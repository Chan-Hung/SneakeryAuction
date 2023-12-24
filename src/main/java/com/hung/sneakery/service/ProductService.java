package com.hung.sneakery.service;

import com.hung.sneakery.dto.ProductDTO;
import com.hung.sneakery.dto.ProductDetailedDTO;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.enums.ECondition;
import com.hung.sneakery.enums.ESorting;
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
     * Get Products By Filter
     *
     * @param pageable   Pageable
     * @param keyword    String
     * @param category   String
     * @param condition  ECondition
     * @param brands     List<String>
     * @param colors     List<String>
     * @param sizes      List<Integer>
     * @param priceStart Long
     * @param priceEnd   Long
     * @param sorting    ESorting
     * @return Page<ProductDTO>
     */
    Page<ProductDTO> getAll(Pageable pageable, String keyword, String category, ECondition condition,
                            List<String> brands, List<String> colors,
                            List<Integer> sizes, Long priceStart, Long priceEnd, ESorting sorting);

    /**
     * Delete Product
     *
     * @param id Long
     * @return BaseResponse
     */
    BaseResponse delete(Long id);
}
