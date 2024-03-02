package com.hung.sneakery.converter.impl;

import com.hung.sneakery.converter.CategoryConverter;
import com.hung.sneakery.converter.PropertyConverter;
import com.hung.sneakery.dto.CategoryDTO;
import com.hung.sneakery.dto.request.CategoryRequest;
import com.hung.sneakery.entity.Category;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CategoryConverterImpl implements CategoryConverter {

    @Resource
    private PropertyConverter propertyConverter;

    @Override
    public CategoryDTO convertToCategoryDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .properties(propertyConverter.convertPropertiesToDTO(category.getProperty()))
                .build();
    }

    @Override
    public Category convertToCategory(CategoryRequest categoryRequest) {
        return Category.builder()
                .name(categoryRequest.getName())
                .property(propertyConverter.convertPropertiesToMap(categoryRequest.getProperties()))
                .build();
    }

    @Override
    public List<CategoryDTO> convertToCategoryDTOList(List<Category> categories) {
        return Optional.ofNullable(categories).orElse(Collections.emptyList())
                .stream().map(this::convertToCategoryDTO).collect(Collectors.toList());
    }
}
