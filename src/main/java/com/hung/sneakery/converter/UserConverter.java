package com.hung.sneakery.converter;

import com.hung.sneakery.dto.UserDTO;
import com.hung.sneakery.entity.User;

public interface UserConverter {

    UserDTO convertToUserDTO(User user);
}
