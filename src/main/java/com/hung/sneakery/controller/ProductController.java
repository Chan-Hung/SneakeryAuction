package com.hung.sneakery.controller;

import com.hung.sneakery.model.Product;
import com.hung.sneakery.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    //Pageination and Filter
    @GetMapping("/products/homepage")
    public List<Product> getProductsHomepage(){
//        try{
//            int page = 0, size = 1;
////            List<Product> products = new ArrayList<Product>();
//            Pageable paging = PageRequest.of(page, size);
//            Page<Product> pageTuts;
//
//            pageTuts = productRepository.findAll(paging);
//
//            List<Product> products = pageTuts.getContent();
//            Map<String, Object> response = new HashMap<>();
//            response.put("tutorials", products);
//            response.put("currentPage", pageTuts.getNumber());
//            response.put("totalItems", pageTuts.getTotalElements());
//            response.put("totalPages", pageTuts.getTotalPages());
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        return productRepository.findAll();
    }

}
