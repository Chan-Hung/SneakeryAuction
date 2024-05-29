package com.hung.sneakery.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ConfigRequest {

    @JsonProperty("key")
    @NotNull
    private String key;

    @JsonProperty("value")
    @NotNull
    private String value;
}
