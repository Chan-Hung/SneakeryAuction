package com.hung.sneakery.data.remotes.services.impl;

import com.hung.sneakery.data.mappers.AddressMapper;
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
import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AddressMapper mapper;
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
        Optional<User> userOpt = userRepository.findById(userId);
        if(!userOpt.isPresent())
            throw new RuntimeException("User not found");

        User user = userOpt.get();
        return new DataResponse<>(mapToUserDTO(user));
    }

    private UserDTO mapToUserDTO(User user){
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setIsActive(user.getIsActive());

        Address address = addressRepository.findAddressByUser(user);
        if (address == null)
            userDTO.setAddress(null);
        else {
            AddressDTO addressDTO = mapper.mapToDTO(address, AddressDTO.class);
            userDTO.setAddress(addressDTO);
        }
        return userDTO;
    }
}
