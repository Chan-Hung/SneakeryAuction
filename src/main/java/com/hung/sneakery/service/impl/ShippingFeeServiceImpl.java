package com.hung.sneakery.service.impl;

import com.hung.sneakery.entity.ShippingFee;
import com.hung.sneakery.repository.ShippingFeeRepository;
import com.hung.sneakery.service.ShippingFeeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ShippingFeeServiceImpl implements ShippingFeeService {

    @Resource
    private ShippingFeeRepository shippingFeeRepository;

    @Override
    public ShippingFee getOne(String originDistrict, String destinationDistrict) {
        return shippingFeeRepository.findShippingFeeByOriginAndDestination(originDistrict, destinationDistrict);
    }
}
