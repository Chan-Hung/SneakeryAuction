package com.hung.sneakery.service.impl;

import com.hung.sneakery.dto.UserDTO;
import com.hung.sneakery.entity.User;
import com.hung.sneakery.exception.NotFoundException;
import com.hung.sneakery.mapper.UserMapper;
import com.hung.sneakery.repository.UserRepository;
import com.hung.sneakery.service.ProfileService;
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
    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        return userMapper.mapToDTOList(users, UserDTO.class);
    }

    @Override
    public UserDTO getOne(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return userMapper.mapToDTO(user, UserDTO.class);
    }
}
