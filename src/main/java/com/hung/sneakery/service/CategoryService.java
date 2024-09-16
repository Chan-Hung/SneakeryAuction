package com.hung.sneakery.service;

import com.hung.sneakery.dto.CategoryDTO;
import com.hung.sneakery.dto.request.CategoryRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    /**
     * Get Category
     * @param id Long
     * @return CategoryDTO
     */
    CategoryDTO getOne(Long id);

    /**
     * Get all Category
     *
     * @param pageable Pageable
     * @return Page<CategoryDTO>
     */
    Page<CategoryDTO> getAll(Pageable pageable);

    /**
     * Create Category
     *
     * @param request AddressRequest
     * @return AddressDTO
     */
    CategoryDTO create(CategoryRequest request);

    /**
     * Update Category
     *
     * @param id      Long
     * @param request AddressRequest
     * @return AddressDTO
     */
    CategoryDTO update(Long id, CategoryRequest request);

    /**
     * Delete Category
     *
     * @param id Long
     * @return Category
     */
    CategoryDTO delete(Long id);
}
