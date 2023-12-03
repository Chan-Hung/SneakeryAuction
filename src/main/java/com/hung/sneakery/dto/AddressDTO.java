package com.hung.sneakery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDTO {

    @JsonProperty("addressId")
    private Long addressId;

    @JsonProperty("homeNumber")
    private String homeNumber;

    @JsonProperty("cityCode")
    private Integer cityCode;

    @JsonProperty("districtCode")
    private Integer districtCode;

    @JsonProperty("wardCode")
    private Integer wardCode;
}
