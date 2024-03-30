package com.hung.sneakery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MediaDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("path")
    private String path;

    @JsonProperty("isThumbnail")
    private Boolean isThumbnail;
}
