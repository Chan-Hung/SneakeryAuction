package com.hung.sneakery.service;

import com.hung.sneakery.entity.Product;
import com.hung.sneakery.entity.ProductImage;

public interface ProductImageService {

    ProductImage upload(byte[] data, Product product, Boolean isThumbnail);
}
