package com.hung.sneakery.service.impl;

import com.hung.sneakery.converter.CategoryConverter;
import com.hung.sneakery.converter.PropertyConverter;
import com.hung.sneakery.dto.CategoryDTO;
import com.hung.sneakery.dto.request.CategoryRequest;
import com.hung.sneakery.entity.Category;
import com.hung.sneakery.exception.NotFoundException;
import com.hung.sneakery.repository.CategoryRepository;
import com.hung.sneakery.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryRepository categoryRepository;

    @Resource
    private CategoryConverter categoryConverter;

    @Resource
    private PropertyConverter propertyConverter;

    private static final String CATEGORY_NOT_FOUND = "Category not found";

    @Override
    public CategoryDTO getOne(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CATEGORY_NOT_FOUND));
        return categoryConverter.convertToCategoryDTO(category);
    }

    @Override
    public Page<CategoryDTO> getAll(Pageable pageable) {
        Page<Category> categoriesPage = categoryRepository.findAll(pageable);
        List<CategoryDTO> categoryDTOs = categoryConverter.convertToCategoryDTOList(categoriesPage.getContent());
        return new PageImpl<>(categoryDTOs, pageable, categoriesPage.getTotalElements());
    }

    @Override
    public CategoryDTO create(CategoryRequest request) {
        Category category = categoryConverter.convertToCategory(request);
        categoryRepository.save(category);
        return categoryConverter.convertToCategoryDTO(category);
    }

    @Override
    public CategoryDTO update(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CATEGORY_NOT_FOUND));
        category.setName(request.getName());
        category.setProperty(propertyConverter.convertPropertiesToMap(request.getProperties()));
        categoryRepository.save(category);
        return categoryConverter.convertToCategoryDTO(category);
    }

    @Override
    public CategoryDTO delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CATEGORY_NOT_FOUND));
        categoryRepository.delete(category);
        return categoryConverter.convertToCategoryDTO(category);
    }
}
