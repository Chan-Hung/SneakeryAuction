package com.hung.sneakery.controller;

import com.hung.sneakery.dto.ProductDetailedDTO;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.enums.ECondition;
import com.hung.sneakery.enums.ESorting;
import com.hung.sneakery.service.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/", "http://localhost:3000", "https://sneakery.vercel.app/", "https://aunction-react-js.vercel.app/"})
@RequestMapping("/products")
public class ProductController {

    @Resource
    private ProductService productService;

    @GetMapping("/allid")
    public List<Long> getAllProductId() {
        return productService.getAllProductsId();
    }

    @GetMapping("/homepage")
    public Map<String, Object> getProductsHomepage() {
        return productService.getProductsHomepage();
    }

    @GetMapping("/{productId}")
    public ProductDetailedDTO getOne(@PathVariable final Long productId) {
        return productService.getOne(productId);
    }

    @GetMapping("/{categoryName}/{page}")
    public Map<String, Object> getProductsByCategory(@PathVariable final String categoryName, @PathVariable final Integer page) {
        return productService.getProductsByCategory(categoryName, page);
    }

    @GetMapping()
    public Map<String, Object> getProductsByFilter(
            @RequestParam(name = "keyword", required = false) final String keyword,
            @RequestParam(name = "category", required = false) final String category,
            @RequestParam(name = "condition", required = false) final ECondition condition,
            @RequestParam(name = "brand", required = false) final List<String> brands,
            @RequestParam(name = "color", required = false) final List<String> colors,
            @RequestParam(name = "size", required = false) final List<Integer> sizes,
            @RequestParam(name = "priceStart", required = false) final Long priceStart,
            @RequestParam(name = "priceEnd", required = false) final Long priceEnd,
            @RequestParam(name = "sorting", required = false) final ESorting sorting) {
        //https://donghohaitrieu.com/danh-muc/dong-ho-nam/?brand=citizen,fossil&pa_kieu-dang=nam&pa_nang-luong=co-automatic
        return productService.getProductsByFilter(keyword, category, condition, brands, colors, sizes, priceStart, priceEnd, sorting);
    }

    @DeleteMapping("/{productId}")
    public BaseResponse delete(@PathVariable final Long productId) {
        return productService.delete(productId);
    }
}
