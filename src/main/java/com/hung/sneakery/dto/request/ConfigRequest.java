package com.hung.sneakery.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ConfigRequest {

    @JsonProperty("extendedMinute")
    private Integer extendedMinute;
}
