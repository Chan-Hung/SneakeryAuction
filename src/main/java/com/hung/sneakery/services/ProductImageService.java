package com.hung.sneakery.services;

import com.hung.sneakery.model.Product;
import com.hung.sneakery.model.ProductImage;

public interface ProductImageService {
    ProductImage upload(byte[] data, Product product, Boolean isThumbnail);
}
