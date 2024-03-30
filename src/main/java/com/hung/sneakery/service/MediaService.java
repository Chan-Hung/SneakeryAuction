package com.hung.sneakery.service;

import com.hung.sneakery.dto.MediaDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {

    /**
     * Upload product images to cloudinary
     *
     * @param thumbnail the thumbnail image
     * @param images    the images
     * @return the list of product image
     */
    List<MediaDTO> uploadImages(MultipartFile thumbnail, MultipartFile[] images);

    /**
     * Upload category icon to cloudinary
     *
     * @param icon the icon image
     * @return the product icon
     */
    MediaDTO uploadIcon(MultipartFile icon);
}
