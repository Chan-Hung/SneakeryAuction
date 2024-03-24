package com.hung.sneakery.controller;


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
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/", "http://localhost:3000", "https://sneakery.vercel.app/", "https://aunction-react-js.vercel.app/"})
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
}
