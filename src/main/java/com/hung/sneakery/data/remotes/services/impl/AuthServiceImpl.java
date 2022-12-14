package com.hung.sneakery.data.remotes.services.impl;

import com.hung.sneakery.data.models.dto.request.EmailRequest;
import com.hung.sneakery.data.models.dto.request.SignInRequest;
import com.hung.sneakery.data.models.dto.request.SignUpRequest;
import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.models.dto.response.DataResponse;
import com.hung.sneakery.data.models.dto.response.JwtResponse;
import com.hung.sneakery.data.models.entities.Role;
import com.hung.sneakery.data.models.entities.User;
import com.hung.sneakery.data.remotes.repositories.RoleRepository;
import com.hung.sneakery.data.remotes.repositories.UserRepository;
import com.hung.sneakery.data.remotes.services.AuthService;
import com.hung.sneakery.data.remotes.services.MailService;
import com.hung.sneakery.utils.config.security.impl.UserDetailsImpl;
import com.hung.sneakery.utils.config.security.jwt.JwtUtils;
import com.hung.sneakery.utils.enums.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    MailService mailService;


    @Override
    public DataResponse<JwtResponse> signIn(SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Generate Jwt Token after signing in
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return new DataResponse<>(new JwtResponse(jwt,
                userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles ));
    }

    @Override
    public BaseResponse signUp(SignUpRequest signUpRequest) throws MessagingException, UnsupportedEncodingException {
        if (userRepository.existsByUsername(signUpRequest.getUsername()))
            throw new RuntimeException("Username has already existed");

        if (userRepository.existsByEmail(signUpRequest.getEmail()))
            throw new RuntimeException("Email is already in use");

        //Create new user's account
        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        mailService.sendVerificationEmail(user);

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        //If roles are null, default assigning to USER
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER);
            if (userRole == null)
                throw new RuntimeException("Error: Role is not found");
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if ("admin".equals(role)) {
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
                    if (adminRole == null)
                        throw new RuntimeException("Error: Role is not found.");
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER);
                    if (userRole == null)
                        throw new RuntimeException("Error: Role is not found.");
                    roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);

        return new BaseResponse(true, "User registered successfully");
    }

    @Override
    public BaseResponse checkEmail(EmailRequest emailRequest) {
        if (userRepository.existsByEmail(emailRequest.getEmail()))
            return new BaseResponse(true, "Email is already existed");
        return new BaseResponse(true, "Email can be used");
    }
}
