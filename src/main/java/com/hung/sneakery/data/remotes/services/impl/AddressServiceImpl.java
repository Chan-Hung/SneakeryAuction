package com.hung.sneakery.data.remotes.services.impl;

import com.hung.sneakery.data.models.dto.request.AddressRequest;
import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.models.entities.Address;
import com.hung.sneakery.data.models.entities.User;
import com.hung.sneakery.data.remotes.repositories.*;
import com.hung.sneakery.data.remotes.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WardRepository wardRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    DistrictRepository districtRepository;

    @Override
    public BaseResponse create(AddressRequest addressRequest) {
        Address address = new Address();
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(userName);


        address.setUser(user);
        address.setWard(wardRepository.findById(addressRequest.getWardId()).get());
        address.setDistrict(districtRepository.findById(addressRequest.getDistrictId()).get());
        address.setCity(cityRepository.findById(addressRequest.getCityId()).get());
        address.setHomeNumber(addressRequest.getHomeNumber());

        addressRepository.save(address);

        return new BaseResponse(true,"Create user address successfully");
    }

    @Override
    public BaseResponse update(AddressRequest addressRequest) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(userName);

        Address address = addressRepository.findByUser(user);
        if(address == null)
            throw new RuntimeException("Address is not existed");
        else {
            address.setWard(wardRepository.findById(addressRequest.getWardId()).get());
            address.setDistrict(districtRepository.findById(addressRequest.getDistrictId()).get());
            address.setCity(cityRepository.findById(addressRequest.getCityId()).get());
            address.setHomeNumber(addressRequest.getHomeNumber());

            addressRepository.save(address);
        }

        return new BaseResponse(true,"Update user address successfully");
    }
}
