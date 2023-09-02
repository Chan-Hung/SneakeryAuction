package com.hung.sneakery.data.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {

    public Long addressId;

    public String homeNumber;

    private String city;

    private String district;

    private String ward;
}
