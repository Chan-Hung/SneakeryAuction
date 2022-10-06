package com.hung.sneakery.security.services;

import com.hung.sneakery.model.User;
import com.hung.sneakery.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//implementing UserDetailsService to easily retrieve Authentication information
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    //Common approach where we only pass a String-based 'username' (or email) argument and
    //return a UserDetails
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //In this case, email is the username of the application
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User Not Found with email: " + email));

        return UserDetailsImpl.build(user);
    }
}
