package com.hung.sneakery.data.remotes.services.impl;

import com.hung.sneakery.data.mappers.UserMapper;
import com.hung.sneakery.data.models.dto.UserDTO;
import com.hung.sneakery.data.models.dto.response.DataResponse;
import com.hung.sneakery.data.models.entities.User;
import com.hung.sneakery.data.remotes.repositories.UserRepository;
import com.hung.sneakery.data.remotes.services.ProfileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Resource
    UserRepository userRepository;

    @Resource
    UserMapper userMapper;

    @Override
    public DataResponse<List<UserDTO>> getAll() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = userMapper.mapToDTOList(users, UserDTO.class);

        return new DataResponse<>(userDTOs);
    }

    @Override
    public DataResponse<UserDTO> getOne(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if(!userOpt.isPresent())
            throw new RuntimeException("User not found");

        User user = userOpt.get();
        return new DataResponse<>(userMapper.mapToDTO(user, UserDTO.class));
    }

}
