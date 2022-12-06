package com.hung.sneakery.services.impl;

import com.hung.sneakery.model.Product;
import com.hung.sneakery.model.ProductImage;
import com.hung.sneakery.payload.response.CloudinaryUploadResponse;
import com.hung.sneakery.repository.ProductImageRepository;
import com.hung.sneakery.services.CloudinaryService;
import com.hung.sneakery.services.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    ProductImageRepository productImageRepository;

    @Autowired
    CloudinaryService cloudinaryService;

    @Override
    public ProductImage upload(byte[] data, Product product, Boolean isThumbnail) {
        try {
            CloudinaryUploadResponse resp = cloudinaryService.upload(data);
            ProductImage image = new ProductImage();
            image.setIsThumbnail(isThumbnail);
            image.setPath(resp.getUrl());
            image.setProduct(product);
            return image;
        } catch (IOException e) {
            throw new RuntimeException(
                    "IOException occurred when upload data to Cloudinary service (" + e.getMessage() + ")");
        }
    }
}
