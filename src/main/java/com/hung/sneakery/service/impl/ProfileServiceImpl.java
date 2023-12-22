package com.hung.sneakery.service.impl;

import com.hung.sneakery.converter.UserConverter;
import com.hung.sneakery.dto.UserDTO;
import com.hung.sneakery.entity.User;
import com.hung.sneakery.exception.NotFoundException;
import com.hung.sneakery.repository.UserRepository;
import com.hung.sneakery.service.ProfileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserConverter userConverter;

    private static final String USER_NOT_FOUND = "User not found";

    @Override
    public Page<UserDTO> getAll(final Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        List<UserDTO> userDTOs = userConverter.convertToUserDTOList(usersPage.getContent());
        return new PageImpl<>(userDTOs, pageable, usersPage.getTotalElements());
    }

    @Override
    public UserDTO getOne(final Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        return userConverter.convertToUserDTO(user);
    }

    @Override
    public UserDTO update(final Long id, final UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        user.setIsActive(userDTO.getIsActive());
        return userConverter.convertToUserDTO(userRepository.save(user));
    }
}
