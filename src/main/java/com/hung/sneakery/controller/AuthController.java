package com.hung.sneakery.controller;


import com.hung.sneakery.dto.request.ResetPasswordRequest;
import com.hung.sneakery.dto.request.SignInRequest;
import com.hung.sneakery.dto.request.SignUpRequest;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.dto.response.JwtResponse;
import com.hung.sneakery.service.AuthService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@Api(tags = "Authentication APIs")
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private AuthService authService;

    @PostMapping("/signin")
    public JwtResponse signIn(@Valid @RequestBody final SignInRequest signinRequest) {
        return authService.signIn(signinRequest);
    }

    @PostMapping("/signup")
    public BaseResponse signUp(@Valid @RequestBody final SignUpRequest signUpRequest) {
        return authService.signUp(signUpRequest);
    }

    @PostMapping("/password/reset")
    public BaseResponse resetPassword(@Valid @RequestBody final ResetPasswordRequest request) {
        return authService.resetPassword(request);
    }

    @GetMapping("/phone-number/verify")
    public BaseResponse verifyPhoneNumber(@RequestParam("phoneNumber") final String phoneNumber) {
        return authService.verifyPhoneNumber(phoneNumber);
    }
}
