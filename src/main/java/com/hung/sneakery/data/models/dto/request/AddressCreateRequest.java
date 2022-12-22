package com.hung.sneakery.data.models.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressCreateRequest {
    public String homeNumber;

    private Long cityId;

    private Long districtId;

    private Long wardId;
}
