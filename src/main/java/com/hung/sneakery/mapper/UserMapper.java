package com.hung.sneakery.mapper;

import com.hung.sneakery.mapper.base.AbstractModelMapper;
import com.hung.sneakery.dto.UserDTO;
import com.hung.sneakery.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends AbstractModelMapper<User, UserDTO> {
}
