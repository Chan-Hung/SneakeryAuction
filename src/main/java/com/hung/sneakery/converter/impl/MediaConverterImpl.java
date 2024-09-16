package com.hung.sneakery.converter.impl;

import com.hung.sneakery.converter.MediaConverter;
import com.hung.sneakery.dto.MediaDTO;
import com.hung.sneakery.entity.Media;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MediaConverterImpl implements MediaConverter {

    @Override
    public MediaDTO convertToMediaDTO(Media media) {
        return MediaDTO.builder()
                .id(media.getId())
                .path(media.getPath())
                .isThumbnail(media.getIsThumbnail())
                .build();
    }

    @Override
    public List<MediaDTO> convertToMediaDTOList(List<Media> medias) {
        return Optional.ofNullable(medias).orElse(Collections.emptyList())
                .stream().map(this::convertToMediaDTO).collect(Collectors.toList());
    }
}
