package com.hung.sneakery.repository.custom;

import com.hung.sneakery.entity.Product;
import com.hung.sneakery.enums.ECondition;
import com.hung.sneakery.enums.ESorting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductCustomRepository {

    Page<Product> productSearch(Pageable pageable, String keyword, String category, ECondition condition, List<String> brands,
                                List<String> colors, List<Integer> sizes, Long priceStart, Long priceEnd, ESorting sorting);
}

