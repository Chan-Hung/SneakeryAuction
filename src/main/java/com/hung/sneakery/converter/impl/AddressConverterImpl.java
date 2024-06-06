package com.hung.sneakery.converter.impl;

import com.hung.sneakery.converter.AddressConverter;
import com.hung.sneakery.dto.AddressDTO;
import com.hung.sneakery.dto.request.AddressRequest;
import com.hung.sneakery.entity.Address;
import com.hung.sneakery.entity.User;
import com.hung.sneakery.utils.SneakeryUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AddressConverterImpl implements AddressConverter {


    @Resource
    private SneakeryUtil sneakeryUtil;

    @Override
    public AddressDTO convertToAddressDTO(Address address) {
        return AddressDTO.builder()
                .addressId(address.getId())
                .homeNumber(address.getHomeNumber())
                .cityCode(address.getCityCode())
                .districtCode(address.getDistrictCode())
                .wardCode(address.getWardCode())
                .phoneNumber(address.getPhoneNumber())
                .build();
    }

    @Override
    public Address convertToAddress(AddressRequest request) {
        User user = sneakeryUtil.getCurrentUser();

        return Address.builder()
                .homeNumber(request.getHomeNumber())
                .wardCode(request.getWardCode())
                .districtCode(request.getDistrictCode())
                .cityCode(request.getCityCode())
                .phoneNumber(request.getPhoneNumber())
                .user(user)
                .build();
    }

    @Override
    public List<AddressDTO> convertToAddressDTOList(List<Address> addresses) {
        return Optional.ofNullable(addresses).orElse(Collections.emptyList())
                .stream().map(this::convertToAddressDTO).collect(Collectors.toList());
    }
}
