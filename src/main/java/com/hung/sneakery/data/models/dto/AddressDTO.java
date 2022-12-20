package com.hung.sneakery.data.models.dto;

import com.hung.sneakery.data.models.entities.City;
import com.hung.sneakery.data.models.entities.District;
import com.hung.sneakery.data.models.entities.Ward;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {

    public Long addressId;

    public String homeNumber;

    private City city;

    private District district;

    private Ward ward;
}
