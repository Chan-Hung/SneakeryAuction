package com.hung.sneakery.service;

import com.hung.sneakery.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProfileService {

    /**
     * Get all UserDTO
     *
     * @return Page<UserDTO>
     */
    Page<UserDTO> getAll(Pageable pageable);

    /**
     * Get User detail
     *
     * @param userId Long
     * @return UserDTO
     */
    UserDTO getOne(Long userId);

    /**
     * Update user
     *
     * @param userId Long
     * @return UserDTO
     */
    UserDTO update(Long userId, UserDTO userDTO);
}
