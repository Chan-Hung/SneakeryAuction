package com.hung.sneakery.data.remotes.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.hung.sneakery.data.models.dto.response.CloudinaryUploadResponse;
import com.hung.sneakery.data.remotes.services.CloudinaryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    public static final String CLOUDINARY_CLOUD_NAME = "dki8kpq7y";
    public static final String CLOUDINARY_API_KEY = "127493437893745";
    public static final String CLOUDINARY_API_SECRET = "BL6qANmHBdpW_pxP2327WXj6WOA";

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
    public CloudinaryUploadResponse upload(byte[] images) throws IOException {
        Map response = cloudinary.uploader().upload(images, ObjectUtils.asMap("resource_type", "auto"));
        return new CloudinaryUploadResponse(
                response.get("secure_url").toString(),
                response.get("public_id").toString(),
                response.get("resource_type").toString());
    }
}
