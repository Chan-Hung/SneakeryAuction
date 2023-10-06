package com.hung.sneakery.service.impl;

import com.hung.sneakery.data.models.dto.ProductDTO;
import com.hung.sneakery.data.models.dto.ProductDetailedDTO;
import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.models.dto.response.DataResponse;
import com.hung.sneakery.data.models.entities.Category;
import com.hung.sneakery.data.models.entities.Product;
import com.hung.sneakery.repository.CategoryRepository;
import com.hung.sneakery.repository.ProductRepository;
import com.hung.sneakery.service.ProductService;
import com.hung.sneakery.exceptions.NotFoundException;
import com.hung.sneakery.utils.enums.ECondition;
import com.hung.sneakery.utils.enums.ESorting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductRepository productRepository;

    @Resource
    private CategoryRepository categoryRepository;

    @Override
    public DataResponse<ProductDetailedDTO> getOne(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(String.format("Product not found with id: %s", productId)));
        ProductDetailedDTO productDetailedDto = new ProductDetailedDTO(product);
        return new DataResponse<>(productDetailedDto);
    }

    //Get ID of all products
    @Override
    public DataResponse<?> getAllProductsId() {
        List<Long> allId = productRepository.getAllId();
        return new DataResponse<>(allId);
    }

    @Override
    public DataResponse<Map<String, Object>> getProductsByCategory(String categoryName, Integer page) {
        //9 products displayed per page
        int pageSize = 9;
        Page<Product> pageTuts;
        Pageable paging = PageRequest.of(page, pageSize);

        //Find Category by categoryName first
        Category category = categoryRepository.findByCategoryName(categoryName);

        if (category == null)
            throw new RuntimeException("Category name: " + categoryName + " is invalid");

        //then pass that product category into findByProductCategory() method
        pageTuts = productRepository.findByCategory(category, paging);
        if (pageTuts.isEmpty()) {
            throw new RuntimeException("Products not found");
        }

        return new DataResponse<>(getMapResponseEntity(pageTuts));
    }

    @Override
    public DataResponse<Map<String, Object>> getProductsHomepage() {
        //Products displayed on homepage
        int size = 40;
        Pageable paging = PageRequest.of(0, size);
        Page<Product> pageTuts;
        pageTuts = productRepository.findAll(paging);
        return new DataResponse<>(getMapResponseEntity(pageTuts));
    }

    @Override
    public DataResponse<Map<String, Object>> getProductsByFilter(String keyword, String category, ECondition condition, List<String> brands, List<String> colors, List<Integer> sizes, Long priceStart, Long priceEnd, ESorting sorting) {
        int sizePage = 9;
        Pageable paging = PageRequest.of(0, sizePage);
        List<Product> pageTuts;

        pageTuts = productRepository.productSearch(keyword, category, condition, brands, colors, sizes, priceStart, priceEnd, sorting, paging);
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : pageTuts) {
            ProductDTO productHomepageDto = new ProductDTO(product);
            productDTOs.add(productHomepageDto);
        }
        if (productDTOs.isEmpty()) {
            throw new RuntimeException("Products not found");
        }
        Map<String, Object> response = new HashMap<>();
        response.put("products", productDTOs);

        return new DataResponse<>(response);
    }

    @Override
    public BaseResponse deleteProduct(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent())
            throw new RuntimeException("Product not found with ID: " + productId);
        productRepository.delete(optionalProduct.get());
        return new BaseResponse(true, "Deleted product successfully");
    }

    //GetMapResponseEntity
    private Map<String, Object> getMapResponseEntity(Page<Product> pageTuts) {
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : pageTuts.getContent()) {
            ProductDTO productHomepageDto = new ProductDTO(product);
            productDTOs.add(productHomepageDto);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("products", productDTOs);
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalItems", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());
        return response;
    }
}
