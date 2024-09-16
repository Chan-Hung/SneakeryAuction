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
     * @param id Long
     * @return UserDTO
     */
    UserDTO getOne(Long id);

    /**
     * Update user
     *
     * @param id Long
     * @return UserDTO
     */
    UserDTO update(Long id, UserDTO userDTO);
}
