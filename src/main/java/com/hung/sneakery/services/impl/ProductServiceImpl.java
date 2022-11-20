package com.hung.sneakery.services.impl;

import com.hung.sneakery.dto.product.ProductDetailedDto;
import com.hung.sneakery.dto.product.ProductDto;
import com.hung.sneakery.model.Category;
import com.hung.sneakery.model.Product;
import com.hung.sneakery.model.ProductImage;
import com.hung.sneakery.model.User;
import com.hung.sneakery.model.datatype.ECondition;
import com.hung.sneakery.payload.response.DataResponse;
import com.hung.sneakery.repository.CategoryRepository;
import com.hung.sneakery.repository.ProductImageRepository;
import com.hung.sneakery.repository.ProductRepository;
import com.hung.sneakery.repository.UserRepository;
import com.hung.sneakery.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductImageRepository productImageRepository;

    @Override
    public DataResponse<ProductDetailedDto> getProductDetailed(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent())
            throw new RuntimeException("Product not found with ID: " + productId);
        ProductDetailedDto productDetailedDto = new ProductDetailedDto(optionalProduct.get());
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

        if(category == null)
            throw new RuntimeException("Category name: "+ categoryName + " is invalid");

        //then pass that product category into findByProductCategory() method
        pageTuts = productRepository.findByCategory(category, paging);
        if(pageTuts.isEmpty()) {
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
    public DataResponse<Map<String, Object>> getProductsByFilter(String keyword, String category, ECondition condition, String brand, String color, String size) {
        int sizePage = 40;
        Pageable paging = PageRequest.of(0, sizePage);
        List<Product> pageTuts;

        pageTuts = productRepository.productSearch(keyword, category, condition, brand, color, size, paging);
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product : pageTuts)
        {
            ProductDto productHomepageDto = new ProductDto(product);
            productDtos.add(productHomepageDto);
        }
        if(productDtos.isEmpty())
        {
            throw new RuntimeException("Products not found");
        }
        Map<String, Object> response = new HashMap<>();
        response.put("products", productDtos);

        return new DataResponse<>(response);
    }

    @Override
    public DataResponse<ProductDetailedDto> createBiddingProduct(Long categoryId, Long sellerId, String productName, ECondition condition, String brand, String color, Integer size, List<ProductImage> productImages, LocalDateTime bidStartingDate, LocalDateTime bidClosingDate, Long priceStart, Long bidIncrement) {
        Category category = categoryRepository.findById(categoryId).get();
        User seller = userRepository.findById(sellerId).get();
        Product product = new Product();
        product.setCondition(condition);

        productImageRepository.saveAll(productImages);



        return null;
    }

    @Override
    public void insertProductImage(List<ProductImage> productImages, Long productId) {
        Product product = productRepository.findById(productId).get();
        for (ProductImage productImage: productImages){
            ProductImage image = new ProductImage();
            image.setProduct(product);
            image.setPath(productImage.getPath());
            if (productImage.getIsThumbnail())
                image.setIsThumbnail(true);
            productImageRepository.save(image);
        }
    }

    //GetMapResponseEntity
    private Map<String, Object> getMapResponseEntity(Page<Product> pageTuts) {
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product : pageTuts.getContent())
        {
            ProductDto productHomepageDto = new ProductDto(product);
            productDtos.add(productHomepageDto);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("products", productDtos);
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalItems", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());
        return response;
    }
}
