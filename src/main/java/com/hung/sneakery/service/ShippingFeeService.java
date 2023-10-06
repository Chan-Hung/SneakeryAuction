package com.hung.sneakery.service;

import com.hung.sneakery.dto.response.DataResponse;
import com.hung.sneakery.entities.ShippingFee;

public interface ShippingFeeService {

    DataResponse<ShippingFee> getOne(String originDistrict, String destinationDistrict);
}
