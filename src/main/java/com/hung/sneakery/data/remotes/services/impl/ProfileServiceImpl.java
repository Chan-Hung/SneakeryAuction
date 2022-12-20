package com.hung.sneakery.data.remotes.services.impl;

import com.hung.sneakery.data.models.dto.AddressDTO;
import com.hung.sneakery.data.models.dto.UserDTO;
import com.hung.sneakery.data.models.dto.response.DataResponse;
import com.hung.sneakery.data.models.entities.Address;
import com.hung.sneakery.data.models.entities.User;
import com.hung.sneakery.data.remotes.repositories.AddressRepository;
import com.hung.sneakery.data.remotes.repositories.UserRepository;
import com.hung.sneakery.data.remotes.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public DataResponse<List<UserDTO>> getAll() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user: users){
            UserDTO userDTO = mapToUserDTO(user);
            userDTOs.add(userDTO);
        }

        return new DataResponse<>(userDTOs);
    }

    @Override
    public DataResponse<UserDTO> getOne(Long userId) {
        return null;
    }

    private UserDTO mapToUserDTO(User user){
        UserDTO userDTO = new UserDTO();

        userDTO.setUserId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setIsActive(user.getIsActive());

        Address address = addressRepository.findAddressByUser(user);
        if (address == null)
            userDTO.setAddress(null);
        AddressDTO addressDTO = AddressServiceImpl.mapToAddressDTO(address);
        userDTO.setAddress(addressDTO);

        return userDTO;
    }
}
