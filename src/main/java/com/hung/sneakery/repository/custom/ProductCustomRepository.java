package com.hung.sneakery.repository.custom;

import com.hung.sneakery.model.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductCustomRepository {
    List<Product> productSearch(String category, String keyword, Pageable pageable);

}

