package com.hung.sneakery.service.impl;

import com.hung.sneakery.data.models.dto.request.SignInRequest;
import com.hung.sneakery.data.models.dto.request.SignUpRequest;
import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.models.dto.response.DataResponse;
import com.hung.sneakery.data.models.dto.response.JwtResponse;
import com.hung.sneakery.data.models.entities.Role;
import com.hung.sneakery.data.models.entities.User;
import com.hung.sneakery.repository.RoleRepository;
import com.hung.sneakery.repository.UserRepository;
import com.hung.sneakery.service.AuthService;
import com.hung.sneakery.service.MailService;
import com.hung.sneakery.utils.config.security.impl.UserDetailsImpl;
import com.hung.sneakery.utils.config.security.jwt.JwtUtils;
import com.hung.sneakery.utils.enums.ERole;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
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

    @Resource
    private MailService mailService;

    @Override
    public DataResponse<JwtResponse> signIn(SignInRequest signInRequest) {
        User user = userRepository.findByEmail(signInRequest.getEmail());
        if (!user.getIsActive()) {
            throw new RuntimeException("User hasn't been activated");
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Generate Jwt Token after signing in
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return new DataResponse<>(new JwtResponse(jwt,
                userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @Override
    public BaseResponse signUp(SignUpRequest signUpRequest) throws MessagingException, UnsupportedEncodingException {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new RuntimeException("Username has already existed");
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }
        String verificationCode = RandomString.make(30);
        //Create new user's account
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .verificationCode(verificationCode)
                .isActive(Boolean.FALSE)
                .build();
        mailService.sendVerificationEmail(user, verificationCode);

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        //If roles are null, default assigning to USER
        if (Objects.isNull(strRoles)) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER);
            if (userRole == null) {
                throw new RuntimeException("Error: Role is not found");
            }
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if ("admin".equals(role)) {
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
                    if (Objects.isNull(adminRole)) {
                        throw new RuntimeException("Error: Role is not found.");
                    }
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER);
                    if (Objects.isNull(userRole)) {
                        throw new RuntimeException("Error: Role is not found.");
                    }
                    roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return new BaseResponse(true, "User registered successfully");
    }

    @Override
    public BaseResponse checkEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            return new BaseResponse(true, "Email is already existed");
        }
        return new BaseResponse(true, "Email can be used");
    }

    @Override
    public BaseResponse verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);
        if (Objects.isNull(user) || user.getIsActive()) {
            return new BaseResponse(false, "User has already been active");
        } else {
            user.setVerificationCode(null);
            user.setIsActive(true);
            userRepository.save(user);
            return new BaseResponse(true, "Activate user successfully");
        }
    }
}
