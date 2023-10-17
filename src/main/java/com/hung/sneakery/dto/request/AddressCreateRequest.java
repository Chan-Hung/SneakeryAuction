package com.hung.sneakery.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressCreateRequest {

    @JsonProperty("homeNumber")
    private String homeNumber;

    @JsonProperty("city")
    private Long city;

    @JsonProperty("district")
    private Long district;

    @JsonProperty("ward")
    private Long ward;
}
