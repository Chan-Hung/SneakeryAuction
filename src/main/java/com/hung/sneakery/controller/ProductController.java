package com.hung.sneakery.controller;

import com.hung.sneakery.dto.product.ProductDetailedDto;
import com.hung.sneakery.dto.product.ProductDto;
import com.hung.sneakery.model.Product;
import com.hung.sneakery.model.ProductCategory;
import com.hung.sneakery.repository.ProductCategoryRepository;
import com.hung.sneakery.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/","http://localhost:3000"})
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    //Pagination and Filter
    @GetMapping("/products/homepage")
    public ResponseEntity<Map<String, Object>> getProductsHomepage(){
        try{
            //Products displayed on homepage
            int size = 40;
            Pageable paging = PageRequest.of(0, size);
            Page<Product> pageTuts;

            //Return Entity if successful
            pageTuts = productRepository.findAll(paging);
            return getMapResponseEntity(pageTuts);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/{id}")
     public ResponseEntity<ProductDetailedDto> getProductDetailed(@PathVariable Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product product = optionalProduct.orElse(null);
        System.out.println(product);
        ProductDetailedDto productDetailedDto = new ProductDetailedDto(product);
        return ResponseEntity.ok(productDetailedDto);
    }

    @GetMapping("/products/{categoryName}/{page}")
    public ResponseEntity<Map<String, Object>> getProductsByCategory(@PathVariable String categoryName,@PathVariable Integer page){
        try{
            //9 products displayed per page
            int size = 9;
            Pageable paging = PageRequest.of(page, size);
            Page<Product> pageTuts;

            //Find Product Category by categoryName first
            ProductCategory productCategory = productCategoryRepository.findByCategoryName(categoryName);

            //then pass that product category into fundByProductCategory() method
            pageTuts = productRepository.findByProductCategory(productCategory, paging);
            return getMapResponseEntity(pageTuts);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //GetMapResponseEntity
    private ResponseEntity<Map<String, Object>> getMapResponseEntity(Page<Product> pageTuts) {
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
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
