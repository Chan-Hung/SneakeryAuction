package com.hung.sneakery.services;

import com.hung.sneakery.payload.request.AddressRequest;
import com.hung.sneakery.payload.response.BaseResponse;

public interface AddressService {
    BaseResponse create(AddressRequest addressRequest);
    BaseResponse update(AddressRequest addressRequest);
}
