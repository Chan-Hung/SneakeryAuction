package com.hung.sneakery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PropertyDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private String type;
}
