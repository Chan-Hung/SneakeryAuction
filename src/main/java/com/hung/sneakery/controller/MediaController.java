package com.hung.sneakery.controller;

import com.hung.sneakery.dto.MediaDTO;
import com.hung.sneakery.service.MediaService;
import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "Media APIs")
@RequestMapping("/medias")
public class MediaController {

    @Resource
    private MediaService mediaService;

    @PostMapping("/upload-images")
    @PreAuthorize("hasRole('USER')")
    public List<MediaDTO> uploadImages(@RequestParam("thumbnail") MultipartFile thumbnail,
                                       @RequestParam("images") MultipartFile[] images) {
        return mediaService.uploadImages(thumbnail, images);
    }

    @PostMapping("/upload-icon")
    @PreAuthorize("hasRole('ADMIN')")
    public MediaDTO uploadIcon(@RequestParam("icon") MultipartFile icon) {
        return mediaService.uploadIcon(icon);
    }
}
