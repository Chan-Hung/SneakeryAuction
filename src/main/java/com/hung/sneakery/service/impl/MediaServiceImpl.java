package com.hung.sneakery.service.impl;

import com.hung.sneakery.converter.MediaConverter;
import com.hung.sneakery.dto.MediaDTO;
import com.hung.sneakery.dto.response.CloudinaryUploadResponse;
import com.hung.sneakery.entity.Media;
import com.hung.sneakery.repository.MediaRepository;
import com.hung.sneakery.service.CloudinaryService;
import com.hung.sneakery.service.MediaService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MediaServiceImpl implements MediaService {

    @Resource
    private MediaRepository mediaRepository;

    @Resource
    private MediaConverter mediaConverter;

    @Resource
    private CloudinaryService cloudinaryService;

    @SneakyThrows
    @Override
    public List<MediaDTO> uploadImages(final MultipartFile thumbnail, final MultipartFile[] images) {
        List<Media> medias = new ArrayList<>();
        CloudinaryUploadResponse response = cloudinaryService.upload(thumbnail);
        Media thumbnailImage = Media.builder()
                .isThumbnail(true)
                .path(response.getUrl())
                .build();
        medias.add(thumbnailImage);

        for (MultipartFile image : images) {
            response = cloudinaryService.upload(image);
            Media productImage = Media.builder()
                    .isThumbnail(false)
                    .path(response.getUrl())
                    .build();
            medias.add(productImage);
        }
        mediaRepository.saveAll(medias);
        return mediaConverter.convertToMediaDTOList(medias);
    }

    @SneakyThrows
    @Override
    public MediaDTO uploadIcon(MultipartFile icon) {
        CloudinaryUploadResponse response = cloudinaryService.upload(icon);
        Media iconImage = Media.builder()
                .isThumbnail(true)
                .path(response.getUrl())
                .build();
        mediaRepository.save(iconImage);
        return mediaConverter.convertToMediaDTO(iconImage);
    }
}
