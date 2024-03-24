package com.hung.sneakery.service.impl;

import com.hung.sneakery.config.security.impl.UserDetailsImpl;
import com.hung.sneakery.config.security.jwt.JwtUtils;
import com.hung.sneakery.dto.request.SignInRequest;
import com.hung.sneakery.dto.request.SignUpRequest;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.dto.response.JwtResponse;
import com.hung.sneakery.entity.Role;
import com.hung.sneakery.entity.User;
import com.hung.sneakery.enums.ERole;
import com.hung.sneakery.exception.AuthenticationException;
import com.hung.sneakery.exception.NotFoundException;
import com.hung.sneakery.repository.RoleRepository;
import com.hung.sneakery.repository.UserRepository;
import com.hung.sneakery.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private UserRepository userRepository;

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private PasswordEncoder encoder;

    @Resource
    private JwtUtils jwtUtils;

    private static final String ROLE_NOT_FOUND = "Role not found";

    @Override
    public JwtResponse signIn(final SignInRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (Objects.isNull(user)) {
            throw new NotFoundException("Email not found");
        }
        if (Boolean.FALSE.equals(user.getIsActive())) {
            throw new AuthenticationException("User hasn't been activated");
        }
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Generate Jwt Token after signing in
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return JwtResponse.builder().token(jwt)
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles).build();
    }

    @Override
    public BaseResponse signUp(final SignUpRequest request) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(request.getUsername()))) {
            throw new AuthenticationException("Username has already existed");
        }
        if (Boolean.TRUE.equals(userRepository.existsByEmail(request.getEmail()))) {
            throw new AuthenticationException("Email is already in use");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .isActive(Boolean.TRUE)
                .build();

        Set<String> strRoles = request.getRole();
        Set<Role> roles = new HashSet<>();
        handleRole(strRoles, roles);
        user.setRoles(roles);
        userRepository.save(user);
        return new BaseResponse("User registered successfully");
    }

    private void handleRole(final Set<String> strRoles, final Set<Role> roles) {
        //If roles are null, default assigning to USER
        if (Objects.isNull(strRoles)) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new NotFoundException(ROLE_NOT_FOUND));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if ("admin".equals(role)) {
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new NotFoundException(ROLE_NOT_FOUND));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new NotFoundException(ROLE_NOT_FOUND));
                    roles.add(userRole);
                }
            });
        }
    }
}
