package com.hung.sneakery.data.remotes.services.impl;

import com.hung.sneakery.data.mappers.AddressMapper;
import com.hung.sneakery.data.models.dto.AddressDTO;
import com.hung.sneakery.data.models.dto.request.AddressCreateRequest;
import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.models.dto.response.DataResponse;
import com.hung.sneakery.data.models.entities.*;
import com.hung.sneakery.data.remotes.repositories.*;
import com.hung.sneakery.data.remotes.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Resource
    AddressRepository addressRepository;

    @Resource
    UserRepository userRepository;

    @Resource
    WardRepository wardRepository;

    @Resource
    CityRepository cityRepository;

    @Resource
    DistrictRepository districtRepository;

    @Resource
    AddressMapper mapper;

    @Override
    public BaseResponse create(AddressCreateRequest addressCreateRequest) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(userName);

        Address address = new Address();
        address.setUser(user);
        address.setHomeNumber(addressCreateRequest.getHomeNumber());

        City city = cityRepository.findById(addressCreateRequest.getCityId()).get();
        if (city == null)
            throw new RuntimeException("City not found");
        address.setCity(city);

        District districtName = districtRepository.findById(addressCreateRequest.getDistrictId()).get();
        District district = districtRepository.findByNameAndCity(districtName.getName(), city);
        if(district == null)
            throw new RuntimeException("District not found with city's name: " + city.getName());
        address.setDistrict(district);

        Ward wardName = wardRepository.findById(addressCreateRequest.getWardId()).get();
        Ward ward = wardRepository.findByNameAndDistrict(wardName.getName(), district);
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

//            City city = cityRepository.findByName(addressDTO.getCityName());
//            if (city == null)
//                throw new RuntimeException("City not found");
//            address.setCity(city);
//
//            District district = districtRepository.findByNameAndCity(addressDTO.getDistrictName(), city);
//            if(district == null)
//                throw new RuntimeException("District not found with city's name: " + city.getName());
//            address.setDistrict(district);
//
//            Ward ward = wardRepository.findByNameAndDistrict(addressDTO.getWardName(), district);
//            if(ward == null)
//                throw new RuntimeException("Ward not found with district's name: " + district.getName());
//            address.setWard(ward);

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

        AddressDTO addressDTO = mapper.mapToDTO(address, AddressDTO.class);
        return new DataResponse<>(addressDTO);
    }

    @Override
    public DataResponse<AddressDTO> getOneByUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(userName);

       Address address = addressRepository.findAddressByUser(user);
        if(address == null)
            throw new RuntimeException("Address not found");

        AddressDTO addressDTO = mapper.mapToDTO(address, AddressDTO.class);
        return new DataResponse<>(addressDTO);
    }

    @Override
    public DataResponse<List<AddressDTO>> getAll() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(userName);

        List<Address> addresses = addressRepository.findByUser(user);

        List<AddressDTO> addressDTOs = mapper.mapToDTOList(addresses, AddressDTO.class);
        return new DataResponse<>(addressDTOs);
    }
}
