package com.hung.sneakery.service;

import com.hung.sneakery.dto.response.CloudinaryUploadResponse;

import java.io.IOException;

public interface CloudinaryService {

    CloudinaryUploadResponse upload(byte[] images) throws IOException;
}
