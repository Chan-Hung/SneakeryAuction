package com.hung.sneakery.service;

import com.hung.sneakery.entities.Product;
import com.hung.sneakery.entities.ProductImage;

public interface ProductImageService {

    ProductImage upload(byte[] data, Product product, Boolean isThumbnail);
}
