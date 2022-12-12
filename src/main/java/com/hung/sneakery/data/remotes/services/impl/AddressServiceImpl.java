package com.hung.sneakery.data.remotes.services.impl;

import com.hung.sneakery.data.models.dto.AddressDTO;
import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.models.dto.response.DataResponse;
import com.hung.sneakery.data.models.entities.*;
import com.hung.sneakery.data.remotes.repositories.*;
import com.hung.sneakery.data.remotes.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public BaseResponse create(AddressDTO addressDTO) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(userName);

        Address address = new Address();
        address.setUser(user);
        address.setHomeNumber(addressDTO.getHomeNumber());

        City city = cityRepository.findByName(addressDTO.getCityName());
        if (city == null)
            throw new RuntimeException("City not found");
        address.setCity(city);

        District district = districtRepository.findByNameAndCity(addressDTO.getDistrictName(), city);
        if(district == null)
            throw new RuntimeException("District not found with city's name: " + city.getName());
        address.setDistrict(district);

        Ward ward = wardRepository.findByNameAndDistrict(addressDTO.getWardName(), district);
        if(ward == null)
            throw new RuntimeException("Ward not found with district's name: " + district.getName());
        address.setWard(ward);

        addressRepository.save(address);

        return new BaseResponse(true,
                "Create's user address successfully");
    }

    @Override
    public BaseResponse update(AddressDTO addressDTO) {
        Optional<Address> optAddress = addressRepository.findById(addressDTO.getAddressId());
        if(!optAddress.isPresent())
            throw new RuntimeException("Address not found");
        else {
            Address address = optAddress.get();
            address.setHomeNumber(addressDTO.getHomeNumber());

            City city = cityRepository.findByName(addressDTO.getCityName());
            if (city == null)
                throw new RuntimeException("City not found");
            address.setCity(city);

            District district = districtRepository.findByNameAndCity(addressDTO.getDistrictName(), city);
            if(district == null)
                throw new RuntimeException("District not found with city's name: " + city.getName());
            address.setDistrict(district);

            Ward ward = wardRepository.findByNameAndDistrict(addressDTO.getWardName(), district);
            if(ward == null)
                throw new RuntimeException("Ward not found with district's name: " + district.getName());
            address.setWard(ward);

            addressRepository.save(address);
        }

        return new BaseResponse(true,
                "Update user's address successfully");
    }

    @Override
    public BaseResponse delete(Long addressId) {
        Optional<Address> optAddress = addressRepository.findById(addressId);

        if(!optAddress.isPresent())
            throw new RuntimeException("Address not found");

        Address address = optAddress.get();
        addressRepository.delete(address);

        return new BaseResponse(true,
                "Delete user's address successfully");
    }

    @Override
    public DataResponse<AddressDTO> getOne(Long addressId) {
        Optional<Address> optAddress = addressRepository.findById(addressId);
        if(!optAddress.isPresent())
            throw new RuntimeException("Address not found");

        Address address = optAddress.get();
        AddressDTO addressDTO = mapToAddressDTO(address);

        return new DataResponse<>(addressDTO);
    }

    @Override
    public DataResponse<AddressDTO> getOneByUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(userName);

       Address address = addressRepository.findAddressByUser(user);
        if(address == null)
            throw new RuntimeException("Address not found");
        AddressDTO addressDTO = mapToAddressDTO(address);
        return new DataResponse<>(addressDTO);
    }

    @Override
    public DataResponse<List<AddressDTO>> getAll() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(userName);

        List<Address> addresses = addressRepository.findByUser(user);

        List<AddressDTO> addressDTOs = new ArrayList<>();
        for(Address address: addresses){
            AddressDTO addressDTO = mapToAddressDTO(address);
            addressDTOs.add(addressDTO);
        }
        return new DataResponse<>(addressDTOs);
    }

    private AddressDTO mapToAddressDTO(Address address){
        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setAddressId(address.getId());
        addressDTO.setHomeNumber(address.getHomeNumber());
        addressDTO.setCityName(address.getCity().getName());
        addressDTO.setDistrictName(address.getDistrict().getName());
        addressDTO.setWardName(address.getWard().getName());

        return addressDTO;
    }
}
