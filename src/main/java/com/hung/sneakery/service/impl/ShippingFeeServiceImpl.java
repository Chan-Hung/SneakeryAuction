package com.hung.sneakery.service.impl;

import com.hung.sneakery.dto.response.DataResponse;
import com.hung.sneakery.data.models.entities.ShippingFee;
import com.hung.sneakery.repository.ShippingFeeRepository;
import com.hung.sneakery.service.ShippingFeeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ShippingFeeServiceImpl implements ShippingFeeService {

    @Resource
    private ShippingFeeRepository shippingFeeRepository;

    @Override
    public DataResponse<ShippingFee> getOne(String originDistrict, String destinationDistrict) {
        ShippingFee shippingFee = shippingFeeRepository.findShippingFeeByOriginAndDestination(originDistrict, destinationDistrict);

        return new DataResponse<>(shippingFee);
    }
}
