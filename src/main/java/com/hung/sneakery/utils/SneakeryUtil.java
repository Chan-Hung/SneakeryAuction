package com.hung.sneakery.utils;

import com.hung.sneakery.entity.User;
import com.hung.sneakery.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SneakeryUtil {

    @Resource
    private UserRepository userRepository;

    public User getCurrentUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(userName);
    }
}
