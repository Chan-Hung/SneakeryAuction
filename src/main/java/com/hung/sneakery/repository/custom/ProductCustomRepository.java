package com.hung.sneakery.repository.custom;

import com.hung.sneakery.model.Product;
import com.hung.sneakery.model.datatype.ECondition;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductCustomRepository {
    List<Product> productSearch(String keyword, String category, ECondition condition, String brand, String color, String size, Pageable pageable);

}

