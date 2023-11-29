package com.hung.sneakery.converter.impl;

import com.hung.sneakery.converter.UserConverter;
import com.hung.sneakery.dto.UserDTO;
import com.hung.sneakery.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverterImpl implements UserConverter {

    @Override
    public UserDTO convertToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .isActive(user.getIsActive())
                .build();
    }
}
