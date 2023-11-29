package com.hung.sneakery.converter.impl;

import com.hung.sneakery.converter.AddressConverter;
import com.hung.sneakery.dto.AddressDTO;
import com.hung.sneakery.entity.Address;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AddressConverterImpl implements AddressConverter {

    @Override
    public AddressDTO convertToAddressDTO(Address address) {
        return AddressDTO.builder()
                .addressId(address.getId())
                .homeNumber(address.getHomeNumber())
                .city(address.getCity())
                .district(address.getDistrict())
                .ward(address.getWard())
                .build();
    }

    @Override
    public List<AddressDTO> convertToAddressDTOList(List<Address> addresses) {
        return Optional.ofNullable(addresses).orElse(Collections.emptyList())
                .stream().map(this::convertToAddressDTO).collect(Collectors.toList());
    }
}
