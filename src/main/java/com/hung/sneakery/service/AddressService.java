package com.hung.sneakery.service;

import com.hung.sneakery.dto.AddressDTO;

import java.util.List;

public interface AddressService {

    AddressDTO getOne(Long id);

    List<AddressDTO> getAll();

    void save();
}
