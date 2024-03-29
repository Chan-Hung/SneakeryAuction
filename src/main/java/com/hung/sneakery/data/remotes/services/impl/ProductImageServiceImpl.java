package com.hung.sneakery.data.remotes.services.impl;

import com.hung.sneakery.data.models.entities.Product;
import com.hung.sneakery.data.models.entities.ProductImage;
import com.hung.sneakery.data.models.dto.response.CloudinaryUploadResponse;
import com.hung.sneakery.data.remotes.repositories.ProductImageRepository;
import com.hung.sneakery.data.remotes.services.CloudinaryService;
import com.hung.sneakery.data.remotes.services.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Resource
    ProductImageRepository productImageRepository;

    @Resource
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
