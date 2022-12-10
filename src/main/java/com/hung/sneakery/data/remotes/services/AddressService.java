package com.hung.sneakery.data.remotes.services;

import com.hung.sneakery.data.models.dto.request.AddressRequest;
import com.hung.sneakery.data.models.dto.response.BaseResponse;

public interface AddressService {
    BaseResponse create(AddressRequest addressRequest);
    BaseResponse update(AddressRequest addressRequest);
}
