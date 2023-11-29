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

    @JsonProperty("city")
    private String city;

    @JsonProperty("district")
    private String district;

    @JsonProperty("ward")
    private String ward;
}
