package com.hung.sneakery.service.impl;

import com.hung.sneakery.dto.response.CloudinaryUploadResponse;
import com.hung.sneakery.entity.Product;
import com.hung.sneakery.entity.ProductImage;
import com.hung.sneakery.service.CloudinaryService;
import com.hung.sneakery.service.ProductImageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

@Service
public class ProductImageServiceImpl implements ProductImageService {
    @Resource
    private CloudinaryService cloudinaryService;

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
