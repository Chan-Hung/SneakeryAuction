package com.hung.sneakery.controllers;


import com.hung.sneakery.data.models.entities.Role;
import com.hung.sneakery.data.models.entities.User;
import com.hung.sneakery.utils.enums.ERole;
import com.hung.sneakery.data.models.dto.request.EmailRequest;
import com.hung.sneakery.data.models.dto.request.SignInRequest;
import com.hung.sneakery.data.models.dto.request.SignUpRequest;
import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.models.dto.response.JwtResponse;
import com.hung.sneakery.data.remotes.repositories.RoleRepository;
import com.hung.sneakery.data.remotes.repositories.UserRepository;
import com.hung.sneakery.utils.config.security.jwt.JwtUtils;
import com.hung.sneakery.utils.config.security.impl.UserDetailsImpl;
import com.hung.sneakery.data.remotes.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/","http://localhost:3000"})
@RequestMapping("/api/auth")
public class AuthController {
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

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInRequest signinRequest){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Generate Jwt Token after signing in
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new JwtResponse(jwt,
                userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles ));
    }

    @PostMapping("/checkemail")
    public ResponseEntity<BaseResponse> checkEmail(@Valid @RequestBody EmailRequest emailRequest) {
        if (userRepository.existsByEmail(emailRequest.getEmail())){
            return ResponseEntity
                    .ok()
                    .body(new BaseResponse(true, "Email is already existed"));
        }
        return ResponseEntity
                .ok()
                .body(new BaseResponse(true, "This email can be used"));
    }

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) throws MessagingException, UnsupportedEncodingException {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new BaseResponse(false, "Username has already existed"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new BaseResponse(true, "Email is already in use"));
        }

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
                    if(adminRole == null)
                        throw new RuntimeException("Error: Role is not found.");
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER);
                    if(userRole == null)
                        throw new RuntimeException("Error: Role is not found.");
                    roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);

        userRepository.save(user);

        return ResponseEntity
                .ok()
                .body(new BaseResponse(true, "User registered successfully"));
    }
}
