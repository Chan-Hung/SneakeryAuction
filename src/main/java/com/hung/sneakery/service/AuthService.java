package com.hung.sneakery.service;

import com.hung.sneakery.dto.request.SignInRequest;
import com.hung.sneakery.dto.request.SignUpRequest;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.dto.response.JwtResponse;

public interface AuthService {

    /**
     * Sign In
     *
     * @param request SignInRequest
     * @return JwtResponse
     */
    JwtResponse signIn(SignInRequest request);

    /**
     * Sign Up
     *
     * @param request SignUpRequest
     * @return BaseResponse
     */
    BaseResponse signUp(SignUpRequest request);
}
