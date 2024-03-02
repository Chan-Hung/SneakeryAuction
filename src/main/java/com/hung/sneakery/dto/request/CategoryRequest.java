package com.hung.sneakery.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hung.sneakery.dto.PropertyDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("properties")
    private List<PropertyDTO> properties;
}
