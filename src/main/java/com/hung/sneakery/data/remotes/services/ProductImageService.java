package com.hung.sneakery.data.remotes.services;

import com.hung.sneakery.data.models.entities.Product;
import com.hung.sneakery.data.models.entities.ProductImage;

public interface ProductImageService {
    ProductImage upload(byte[] data, Product product, Boolean isThumbnail);
}
