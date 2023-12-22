package com.hung.sneakery.service;

import com.hung.sneakery.dto.response.CloudinaryUploadResponse;

import java.io.IOException;

public interface CloudinaryService {

    /**
     * Upload images to Cloudinary
     *
     * @param images byte[]
     * @return CloudinaryUploadResponse
     * @throws IOException IOException
     */
    CloudinaryUploadResponse upload(byte[] images) throws IOException;
}
