package com.hung.sneakery.config.security.impl;

import com.hung.sneakery.entity.User;
import com.hung.sneakery.exception.NotFoundException;
import com.hung.sneakery.repository.UserRepository;
import com.hung.sneakery.utils.SneakeryConstant;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

//implementing UserDetailsService to easily retrieve Authentication information
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    UserRepository userRepository;

    //Common approach where we only pass a String-based 'username' (or email) argument and
    //return a UserDetails
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //In this case, email is the username of the application
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(SneakeryConstant.USER_NOT_FOUND));
        return UserDetailsImpl.build(user);
    }
}
