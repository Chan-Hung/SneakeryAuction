package com.hung.sneakery.converter;

import com.hung.sneakery.dto.MediaDTO;
import com.hung.sneakery.entity.Media;

import java.util.List;

public interface MediaConverter {


    MediaDTO convertToMediaDTO(Media media);

    List<MediaDTO> convertToMediaDTOList(List<Media> medias);
}
