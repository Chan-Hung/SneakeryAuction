package com.hung.sneakery.service;

import com.hung.sneakery.dto.AddressDTO;
import com.hung.sneakery.dto.request.AddressRequest;

import java.util.List;

public interface AddressService {

    AddressDTO getOne(Long id);

    List<AddressDTO> getAll();

    AddressDTO create(AddressRequest request);

    AddressDTO update(Long id, AddressRequest request);

    AddressDTO delete(Long id);
}
