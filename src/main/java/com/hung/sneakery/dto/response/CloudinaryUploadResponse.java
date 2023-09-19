package com.hung.sneakery.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CloudinaryUploadResponse {
    private String url;
    private String publicId;
    private String resourceType;

    public CloudinaryUploadResponse(String url, String publicId, String resourceType){
        if (url.isEmpty())
            throw new RuntimeException("Upload file to cloudinary failed (no URL to access uploaded file in Cloudinary response)");

        if (publicId.isEmpty())
            throw new RuntimeException("Upload file to cloudinary failed (no publicId to access uploaded file in Cloudinary response)");

        if(resourceType.isEmpty())
            throw new RuntimeException("Upload file to cloudinary failed (no resourceType to access uploaded file in Cloudinary response)");

        this.url = url;
        this.publicId = publicId;
        this.resourceType = resourceType;
    }
}

