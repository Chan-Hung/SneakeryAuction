package com.hung.sneakery.service;

import com.hung.sneakery.dto.response.DataResponse;
import com.hung.sneakery.data.models.entities.ShippingFee;

public interface ShippingFeeService {

    DataResponse<ShippingFee> getOne(String originDistrict, String destinationDistrict);
}
