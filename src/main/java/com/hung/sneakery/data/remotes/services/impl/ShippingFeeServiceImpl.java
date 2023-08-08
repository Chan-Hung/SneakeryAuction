package com.hung.sneakery.data.remotes.services.impl;

import com.hung.sneakery.data.models.dto.response.DataResponse;
import com.hung.sneakery.data.models.entities.ShippingFee;
import com.hung.sneakery.data.remotes.repositories.ShippingFeeRepository;
import com.hung.sneakery.data.remotes.services.ShippingFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ShippingFeeServiceImpl implements ShippingFeeService {

    @Resource
    ShippingFeeRepository shippingFeeRepository;

    @Override
    public DataResponse<ShippingFee> getOne(String originDistrict, String destinationDistrict) {
        ShippingFee shippingFee = shippingFeeRepository.findShippingFeeByOriginAndDestination(originDistrict, destinationDistrict);

        return new DataResponse<>(shippingFee);
    }
}
