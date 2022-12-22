package com.hung.sneakery.data.remotes.services;

import com.hung.sneakery.data.models.dto.response.DataResponse;
import com.hung.sneakery.data.models.entities.ShippingFee;

public interface ShippingFeeService {
    DataResponse<ShippingFee> getOne(String originDistrict, String destinationDistrict);
}
