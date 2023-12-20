package com.hung.sneakery.converter;

import com.hung.sneakery.dto.UserDTO;
import com.hung.sneakery.entity.User;

import java.util.List;

public interface UserConverter {

    UserDTO convertToUserDTO(User user);

    List<UserDTO> convertToUserDTOList(List<User> users);
}
