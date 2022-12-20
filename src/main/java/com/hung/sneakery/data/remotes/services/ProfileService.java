package com.hung.sneakery.data.remotes.services;

import com.hung.sneakery.data.models.dto.UserDTO;
import com.hung.sneakery.data.models.dto.response.DataResponse;

import java.util.List;

public interface ProfileService {
    DataResponse<List<UserDTO>> getAll();

    DataResponse<UserDTO> getOne(Long userId);
}
