package com.hung.sneakery.service.impl;

import com.hung.sneakery.data.mappers.UserMapper;
import com.hung.sneakery.data.models.dto.UserDTO;
import com.hung.sneakery.data.models.dto.response.DataResponse;
import com.hung.sneakery.data.models.entities.User;
import com.hung.sneakery.data.remotes.repositories.UserRepository;
import com.hung.sneakery.service.ProfileService;
import com.hung.sneakery.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserMapper userMapper;

    @Override
    public DataResponse<List<UserDTO>> getAll() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = userMapper.mapToDTOList(users, UserDTO.class);

        return new DataResponse<>(userDTOs);
    }

    @Override
    public DataResponse<UserDTO> getOne(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return new DataResponse<>(userMapper.mapToDTO(user, UserDTO.class));
    }

}
