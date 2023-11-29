package com.hung.sneakery.converter;

import com.hung.sneakery.dto.AddressDTO;
import com.hung.sneakery.entity.Address;

import java.util.List;

public interface AddressConverter {

    AddressDTO convertToAddressDTO(Address address);

    List<AddressDTO> convertToAddressDTOList(List<Address> addresses);
}
