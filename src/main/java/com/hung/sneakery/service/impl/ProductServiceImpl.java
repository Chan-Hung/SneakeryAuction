package com.hung.sneakery.service.impl;

import com.hung.sneakery.converter.ProductConverter;
import com.hung.sneakery.converter.ProductDetailedConverter;
import com.hung.sneakery.dto.ProductDTO;
import com.hung.sneakery.dto.ProductDetailedDTO;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.entity.Category;
import com.hung.sneakery.entity.Product;
import com.hung.sneakery.enums.ECondition;
import com.hung.sneakery.exception.NotFoundException;
import com.hung.sneakery.repository.CategoryRepository;
import com.hung.sneakery.repository.ProductRepository;
import com.hung.sneakery.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductRepository productRepository;

    @Resource
    private CategoryRepository categoryRepository;

    @Resource
    private ProductConverter productConverter;

    @Resource
    private ProductDetailedConverter productDetailedConverter;

    @Override
    public ProductDetailedDTO getOne(final Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(String.format("Product not found with id: %s", productId)));
        return productDetailedConverter.convertToProductDetailedDTO(product);
    }

    @Override
    public List<Long> getAllProductsId() {
        return productRepository.getAllId();
    }

    @Override
    public Page<ProductDTO> getProductsByCategory(final Pageable pageable, final String categoryName) {
        Category category = categoryRepository.findByName(categoryName);
        if (Objects.isNull(category)) {
            throw new NotFoundException("Category name: " + categoryName + " is invalid");
        }
        Page<Product> productPage = productRepository.findByCategory(pageable, category);
        List<ProductDTO> productDTOs = productConverter.convertToProductDTOList(productPage.getContent());
        return new PageImpl<>(productDTOs, pageable, productPage.getTotalElements());
    }

    @Override
    public Page<ProductDTO> getProductsHomepage(final Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        List<ProductDTO> productDTOs = productConverter.convertToProductDTOList(productPage.getContent());
        return new PageImpl<>(productDTOs, pageable, productPage.getTotalElements());
    }

    @Override
    public Page<ProductDTO> getProductsByFilter(final Pageable pageable, final String category, final ECondition condition, final List<String> brands,
                                                final List<String> colors, final List<Integer> sizes, final Long priceStart, final Long priceEnd) {

        Page<Product> productPage = productRepository.productSearch(pageable, category, condition, brands, colors, sizes, priceStart, priceEnd);
        List<ProductDTO> productDTOs = productConverter.convertToProductDTOList(productPage.getContent());
        return new PageImpl<>(productDTOs, pageable, productPage.getTotalElements());
    }

    @Override
    @Transactional
    public BaseResponse delete(final Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        productRepository.delete(product);
        return new BaseResponse(true, "Deleted product successfully");
    }

    @Override
    public Page<ProductDTO> getProductsBySearch(final Pageable pageable, final String productName) {
        Page<Product> productPage = productRepository.findByNameIsContaining(pageable, productName);
        List<ProductDTO> productDTOs = productConverter.convertToProductDTOList(productPage.getContent());
        return new PageImpl<>(productDTOs, pageable, productPage.getTotalElements());
    }
}
