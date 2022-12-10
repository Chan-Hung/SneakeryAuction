package com.hung.sneakery.utils.config.security.impl;

import com.hung.sneakery.data.models.entities.User;
import com.hung.sneakery.data.remotes.repositories.UserRepository;
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
        if(userRepository.findByEmail(email) == null)
            throw new UsernameNotFoundException("User Not Found with email: " + email);
        User user = userRepository.findByEmail(email);

        return UserDetailsImpl.build(user);
    }
}
