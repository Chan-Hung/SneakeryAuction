package com.hung.sneakery.service.impl;

import com.hung.sneakery.converter.AddressConverter;
import com.hung.sneakery.dto.AddressDTO;
import com.hung.sneakery.dto.request.AddressRequest;
import com.hung.sneakery.entity.Address;
import com.hung.sneakery.entity.User;
import com.hung.sneakery.exception.NotFoundException;
import com.hung.sneakery.repository.AddressRepository;
import com.hung.sneakery.repository.UserRepository;
import com.hung.sneakery.service.AddressService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Resource
    private AddressRepository addressRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private AddressConverter addressConverter;

    @Override
    public AddressDTO getOne(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Address not found"));
        return addressConverter.convertToAddressDTO(address);
    }

    @Override
    public List<AddressDTO> getAll() {
        List<Address> addresses = addressRepository.findAll();
        return addressConverter.convertToAddressDTOList(addresses);
    }

    @Override
    public AddressDTO create(AddressRequest request) {
        Address address = Address.builder()
                .homeNumber(request.getHomeNumber())
                .wardCode(request.getWardCode())
                .districtCode(request.getDistrictCode())
                .cityCode(request.getCityCode())
                .phoneNumber(request.getPhoneNumber())
                .build();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        address.setUser(user);
        addressRepository.save(address);
        return addressConverter.convertToAddressDTO(address);
    }

    @Override
    public AddressDTO update(Long id, AddressRequest request) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Address not found"));
        address.setCityCode(request.getCityCode());
        address.setDistrictCode(request.getDistrictCode());
        address.setWardCode(request.getWardCode());
        address.setHomeNumber(request.getHomeNumber());
        address.setPhoneNumber(request.getPhoneNumber());
        addressRepository.save(address);
        return addressConverter.convertToAddressDTO(address);
    }

    @Override
    public AddressDTO delete(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Address not found"));
        addressRepository.delete(address);
        return addressConverter.convertToAddressDTO(address);
    }
}
