package com.hung.sneakery.service;

import com.hung.sneakery.entity.ShippingFee;

public interface ShippingFeeService {

    /**
     * Get Shipping Fee (Highly will use 3rd Party API)
     *
     * @param originDistrict      String
     * @param destinationDistrict String
     * @return ShippingFee
     */
    ShippingFee getOne(String originDistrict, String destinationDistrict);
}
