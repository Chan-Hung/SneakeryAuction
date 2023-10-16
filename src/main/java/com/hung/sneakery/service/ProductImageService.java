package com.hung.sneakery.service;

import com.hung.sneakery.entity.Product;
import com.hung.sneakery.entity.ProductImage;

public interface ProductImageService {

    /**
     * Upload Image
     *
     * @param data byte[]
     * @param product Product
     * @param isThumbnail Boolean
     * @return ProductImage
     */
    ProductImage upload(byte[] data, Product product, Boolean isThumbnail);
}
