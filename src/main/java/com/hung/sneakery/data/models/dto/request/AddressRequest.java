package com.hung.sneakery.data.models.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AddressRequest {
    @NotBlank
    public String homeNumber;

    @NotBlank
    private Long cityId;

    @NotBlank
    private Long districtId;

    @NotBlank
    private Long wardId;
}
