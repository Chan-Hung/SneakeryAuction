package com.hung.sneakery.converter.impl;

import com.hung.sneakery.converter.AddressConverter;
import com.hung.sneakery.converter.UserConverter;
import com.hung.sneakery.dto.UserDTO;
import com.hung.sneakery.entity.User;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserConverterImpl implements UserConverter {

    @Resource
    private AddressConverter addressConverter;

    @Override
    public UserDTO convertToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .isActive(user.getIsActive())
                .build();
    }

    @Override
    public List<UserDTO> convertToUserDTOList(List<User> users) {
        return Optional.ofNullable(users).orElse(Collections.emptyList())
                .stream().map(this::convertToUserDTO).collect(Collectors.toList());
    }
}
