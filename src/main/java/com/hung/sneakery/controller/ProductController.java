package com.hung.sneakery.controller;

import com.hung.sneakery.model.datatype.ECondition;
import com.hung.sneakery.payload.response.BaseResponse;
import com.hung.sneakery.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> getAllProductId(){
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
    public ResponseEntity<?> getProductsHomepage(){
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
     public ResponseEntity<?> getProductDetailed(@PathVariable Long productId){
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
    public ResponseEntity<?> getProductsByCategory(@PathVariable String categoryName,@PathVariable Integer page){
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
    public ResponseEntity<?> getProductsByFilter(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "category",required = false) String category,
            @RequestParam(name = "condition", required = false) ECondition condition,
            @RequestParam(name = "brand", required = false) List<String> brands,
            @RequestParam(name = "color", required = false) List<String> colors,
            @RequestParam(name = "size", required = false) List<Integer> sizes,
            @RequestParam(name = "priceStart", required = false) Long priceStart,
            @RequestParam(name = "priceEnd", required = false) Long priceEnd
            ) {
        //https://donghohaitrieu.com/danh-muc/dong-ho-nam/?brand=citizen,fossil&pa_kieu-dang=nam&pa_nang-luong=co-automatic
        try {
            return ResponseEntity
                    .ok(productService.getProductsByFilter(keyword, category, condition, brands, colors, sizes, priceStart, priceEnd));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse(false,
                            e.getMessage()));
        }
    }



}
