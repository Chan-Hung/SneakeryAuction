package com.hung.sneakery.data.remotes.repositories.custom;

import com.hung.sneakery.data.models.entities.Product;
import com.hung.sneakery.utils.enums.ECondition;
import com.hung.sneakery.utils.enums.ESorting;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductCustomRepository {
    List<Product> productSearch(String keyword, String category, ECondition condition, List<String> brands, List<String> colors, List<Integer> sizes, Long priceStart, Long priceEnd, ESorting sorting, Pageable pageable);
}

