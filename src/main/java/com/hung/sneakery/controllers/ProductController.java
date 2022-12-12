package com.hung.sneakery.controllers;

import com.hung.sneakery.utils.enums.ECondition;
import com.hung.sneakery.utils.enums.ESorting;
import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.remotes.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/","http://localhost:3000"})
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/allid")
    public ResponseEntity<BaseResponse> getAllProductId(){
        try {
            return ResponseEntity
                    .ok(productService.getAllProductsId());
        }
        catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse(false,
                            e.getMessage()));
        }
    }

    //Pagination and Filter
    @GetMapping("/homepage")
    public ResponseEntity<BaseResponse> getProductsHomepage(){
        try {
            return ResponseEntity
                    .ok(productService.getProductsHomepage());
        }
        catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse(false,
                            e.getMessage()));
        }
    }

    @GetMapping("/{productId}")
    @Cacheable(value = "products",key = "#productId")
    public ResponseEntity<BaseResponse> getProductDetailed(@PathVariable Long productId){
        try {
            return ResponseEntity
                    .ok(productService.getProductDetailed(productId));
        }
        catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse(false,
                            e.getMessage()));
        }
    }

    @GetMapping("/{categoryName}/{page}")
    public ResponseEntity<BaseResponse> getProductsByCategory(@PathVariable String categoryName,@PathVariable Integer page){
        try{
            return ResponseEntity
                    .ok(productService.getProductsByCategory(categoryName,page));
        }catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse(false,
                            e.getMessage()));
        }
    }

    @GetMapping()
    public ResponseEntity<BaseResponse> getProductsByFilter(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "category",required = false) String category,
            @RequestParam(name = "condition", required = false) ECondition condition,
            @RequestParam(name = "brand", required = false) List<String> brands,
            @RequestParam(name = "color", required = false) List<String> colors,
            @RequestParam(name = "size", required = false) List<Integer> sizes,
            @RequestParam(name = "priceStart", required = false) Long priceStart,
            @RequestParam(name = "priceEnd", required = false) Long priceEnd,
            @RequestParam(name = "sorting", required = false) ESorting sorting) {
        //https://donghohaitrieu.com/danh-muc/dong-ho-nam/?brand=citizen,fossil&pa_kieu-dang=nam&pa_nang-luong=co-automatic
        try {
            return ResponseEntity
                    .ok(productService.getProductsByFilter(keyword, category, condition, brands, colors, sizes, priceStart, priceEnd, sorting));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse(false,
                            e.getMessage()));
        }
    }



}