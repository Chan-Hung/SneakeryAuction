package com.hung.sneakery.services;

import com.hung.sneakery.payload.response.CloudinaryUploadResponse;

import java.io.IOException;

public interface CloudinaryService {
    CloudinaryUploadResponse upload(byte[] images) throws IOException;
}
