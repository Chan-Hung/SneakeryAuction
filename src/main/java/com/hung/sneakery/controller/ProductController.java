package com.hung.sneakery.controller;

import com.hung.sneakery.dto.ProductDTO;
import com.hung.sneakery.dto.ProductDetailedDTO;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.enums.ECondition;
import com.hung.sneakery.service.ProductService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "Product APIs")
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
    public Page<ProductDTO> getProductsHomepage(final Pageable pageable) {
        return productService.getProductsHomepage(pageable);
    }

    @GetMapping("/{id}")
    public ProductDetailedDTO getOne(@PathVariable final Long id) {
        return productService.getOne(id);
    }

    @GetMapping
    public Page<ProductDTO> getAll(
            final Pageable pageable,
            @RequestParam(name = "keyword", required = false) final String keyword,
            @RequestParam(name = "category", required = false) final String category,
            @RequestParam(name = "condition", required = false) final ECondition condition,
            @RequestParam(name = "brand", required = false) final List<String> brands,
            @RequestParam(name = "color", required = false) final List<String> colors,
            @RequestParam(name = "sizes", required = false) final List<Integer> sizes,
            @RequestParam(name = "priceStart", required = false) final Long priceStart,
            @RequestParam(name = "priceEnd", required = false) final Long priceEnd) {
        return productService.getAll(pageable, keyword, category, condition, brands, colors, sizes, priceStart, priceEnd);
    }

    @DeleteMapping("/{id}")
    public BaseResponse delete(@PathVariable final Long id) {
        return productService.delete(id);
    }
}
