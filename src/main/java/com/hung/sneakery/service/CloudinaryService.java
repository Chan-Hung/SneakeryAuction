package com.hung.sneakery.service;

import com.hung.sneakery.dto.response.CloudinaryUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {

    /**
     * Upload images to Cloudinary
     *
     * @param file MultipartFile
     * @return CloudinaryUploadResponse
     * @throws IOException IOException
     */
    CloudinaryUploadResponse upload(MultipartFile file) throws IOException;
}
