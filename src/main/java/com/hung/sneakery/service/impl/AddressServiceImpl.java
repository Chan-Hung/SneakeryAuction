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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    private static final String ADDRESS_NOT_FOUND = "Address not found";

    @Override
    public AddressDTO getOne(final Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ADDRESS_NOT_FOUND));
        return addressConverter.convertToAddressDTO(address);
    }

    @Override
    public Page<AddressDTO> getAll(final Pageable pageable) {
        Page<Address> addressesPage = addressRepository.findAll(pageable);
        List<AddressDTO> addressDTOs = addressConverter.convertToAddressDTOList(addressesPage.getContent());
        return new PageImpl<>(addressDTOs, pageable, addressesPage.getTotalElements());
    }

    @Override
    public AddressDTO create(final AddressRequest request) {
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
    public AddressDTO update(final Long id, final AddressRequest request) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ADDRESS_NOT_FOUND));
        address.setCityCode(request.getCityCode());
        address.setDistrictCode(request.getDistrictCode());
        address.setWardCode(request.getWardCode());
        address.setHomeNumber(request.getHomeNumber());
        address.setPhoneNumber(request.getPhoneNumber());
        addressRepository.save(address);
        return addressConverter.convertToAddressDTO(address);
    }

    @Override
    public AddressDTO delete(final Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ADDRESS_NOT_FOUND));
        addressRepository.delete(address);
        return addressConverter.convertToAddressDTO(address);
    }
}
