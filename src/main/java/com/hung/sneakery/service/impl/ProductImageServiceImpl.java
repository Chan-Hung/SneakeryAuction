package com.hung.sneakery.service.impl;

import com.hung.sneakery.dto.response.CloudinaryUploadResponse;
import com.hung.sneakery.entity.Product;
import com.hung.sneakery.entity.ProductImage;
import com.hung.sneakery.exception.UploadImageException;
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
    public ProductImage upload(final byte[] data, final Product product, final Boolean isThumbnail) {
        try {
            CloudinaryUploadResponse response = cloudinaryService.upload(data);
            return ProductImage.builder()
                    .isThumbnail(isThumbnail)
                    .path(response.getUrl())
                    .product(product)
                    .build();
        } catch (IOException e) {
            throw new UploadImageException(e.getMessage());
        }
    }
}
