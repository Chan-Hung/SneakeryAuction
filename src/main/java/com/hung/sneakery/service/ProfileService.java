package com.hung.sneakery.service;

import com.hung.sneakery.dto.UserDTO;
import com.hung.sneakery.dto.response.DataResponse;

import java.util.List;

public interface ProfileService {

    DataResponse<List<UserDTO>> getAll();

    DataResponse<UserDTO> getOne(Long userId);
}
