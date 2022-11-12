package com.hung.sneakery.repository.custom;

import com.hung.sneakery.model.Product;

import java.util.List;

public interface ProductCustomRepository {
    public List<Product> productSearch(String category, String keyword);

}

