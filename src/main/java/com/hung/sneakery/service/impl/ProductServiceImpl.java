package com.hung.sneakery.service.impl;

import com.hung.sneakery.converter.ProductConverter;
import com.hung.sneakery.dto.ProductDTO;
import com.hung.sneakery.dto.ProductDetailedDTO;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.entity.Category;
import com.hung.sneakery.entity.Product;
import com.hung.sneakery.enums.ECondition;
import com.hung.sneakery.enums.ESorting;
import com.hung.sneakery.exception.NotFoundException;
import com.hung.sneakery.repository.CategoryRepository;
import com.hung.sneakery.repository.ProductRepository;
import com.hung.sneakery.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductRepository productRepository;

    @Resource
    private CategoryRepository categoryRepository;

    @Resource
    private ProductConverter productConverter;

    @Override
    public ProductDetailedDTO getOne(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(String.format("Product not found with id: %s", productId)));
        return new ProductDetailedDTO(product);
    }

    //Get ID of all products
    @Override
    public List<Long> getAllProductsId() {
        return productRepository.getAllId();
    }

    @Override
    public Map<String, Object> getProductsByCategory(String categoryName, Integer page) {
        //9 products displayed per page
        int pageSize = 9;
        //Find Category by categoryName first
        Category category = categoryRepository.findByName(categoryName);
        if (category == null) {
            throw new NotFoundException("Category name: " + categoryName + " is invalid");
        }
        //then pass that product category into findByProductCategory() method
        Pageable paging = PageRequest.of(page, pageSize);
        Page<Product> pageTuts = productRepository.findByCategory(category, paging);
        if (pageTuts.isEmpty()) {
            throw new NotFoundException("Products not found");
        }
        return getMapResponseEntity(pageTuts);
    }

    @Override
    public Map<String, Object> getProductsHomepage() {
        //Products displayed on homepage
        int size = 20;
        Pageable paging = PageRequest.of(0, size);
        Page<Product> pageTuts = productRepository.findAll(paging);
        return getMapResponseEntity(pageTuts);
    }

    @Override
    public Map<String, Object> getProductsByFilter(String keyword, String category, ECondition condition, List<String> brands, List<String> colors, List<Integer> sizes, Long priceStart, Long priceEnd, ESorting sorting) {
        int sizePage = 9;
        Pageable paging = PageRequest.of(0, sizePage);
        List<Product> pageTuts = productRepository.productSearch(keyword, category, condition, brands, colors, sizes, priceStart, priceEnd, sorting, paging);
        List<ProductDTO> productDTOs = productConverter.convertToProductDTOList(pageTuts);
        Map<String, Object> response = new HashMap<>();
        response.put("products", productDTOs);

        return response;
    }

    @Override
    public BaseResponse delete(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + productId));
        productRepository.delete(product);
        return new BaseResponse(true, "Deleted product successfully");
    }

    //GetMapResponseEntity
    private Map<String, Object> getMapResponseEntity(Page<Product> pageTuts) {
        List<ProductDTO> productDTOs = productConverter.convertToProductDTOList(pageTuts.getContent());
        Map<String, Object> response = new HashMap<>();
        response.put("products", productDTOs);
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalItems", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());
        return response;
    }
}
