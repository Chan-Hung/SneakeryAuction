package com.hung.sneakery.service;

import com.hung.sneakery.dto.UserDTO;

import java.util.List;

public interface ProfileService {

    List<UserDTO> getAll();

    UserDTO getOne(Long userId);
}
