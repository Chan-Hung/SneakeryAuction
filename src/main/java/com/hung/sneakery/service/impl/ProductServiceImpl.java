package com.hung.sneakery.service.impl;

import com.hung.sneakery.converter.ProductConverter;
import com.hung.sneakery.converter.ProductDetailedConverter;
import com.hung.sneakery.dto.ProductDTO;
import com.hung.sneakery.dto.ProductDetailedDTO;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.entity.Product;
import com.hung.sneakery.enums.ECondition;
import com.hung.sneakery.enums.ESorting;
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
    public Page<ProductDTO> getProductsHomepage(final Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        List<ProductDTO> productDTOs = productConverter.convertToProductDTOList(productPage.getContent());
        return new PageImpl<>(productDTOs, pageable, productPage.getTotalElements());
    }

    @Override
    public Page<ProductDTO> getAll(final Pageable pageable, final String keyword, final String category, final ECondition condition, final List<String> brands,
                                   final List<String> colors, final List<Integer> sizes, final Long priceStart, final Long priceEnd, final ESorting sorting) {
        Page<Product> productPage = productRepository.productSearch(pageable, keyword, category, condition, brands, colors, sizes, priceStart, priceEnd, sorting);
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
}
