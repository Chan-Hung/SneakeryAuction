package com.hung.sneakery.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.hung.sneakery.dto.response.CloudinaryUploadResponse;
import com.hung.sneakery.service.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private static final String CLOUDINARY_CLOUD_NAME = System.getenv("CLOUDINARY_CLOUD_NAME");
    private static final String CLOUDINARY_API_KEY = System.getenv("CLOUDINARY_API_KEY");
    private static final String CLOUDINARY_API_SECRET = System.getenv("CLOUDINARY_API_SECRET");

    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", CLOUDINARY_CLOUD_NAME,
                "api_key", CLOUDINARY_API_KEY,
                "api_secret", CLOUDINARY_API_SECRET,
                "secure", true
        ));
    }

    @Override
    public CloudinaryUploadResponse upload(MultipartFile file) throws IOException {
        Map response = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
        return new CloudinaryUploadResponse(
                response.get("secure_url").toString(),
                response.get("public_id").toString(),
                response.get("resource_type").toString());
    }
}
