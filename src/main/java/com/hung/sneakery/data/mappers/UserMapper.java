package com.hung.sneakery.data.mappers;

import com.hung.sneakery.data.mappers.base.AbstractModelMapper;
import com.hung.sneakery.dto.UserDTO;
import com.hung.sneakery.data.models.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends AbstractModelMapper<User, UserDTO> {
}
