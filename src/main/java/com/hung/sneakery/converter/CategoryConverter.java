package com.hung.sneakery.converter;

import com.hung.sneakery.dto.CategoryDTO;
import com.hung.sneakery.dto.request.CategoryRequest;
import com.hung.sneakery.entity.Category;

import java.util.List;

public interface CategoryConverter {

    CategoryDTO convertToCategoryDTO(Category category);

    List<CategoryDTO> convertToCategoryDTOList(List<Category> categories);

    Category convertToCategory(CategoryRequest categoryRequest);
}
