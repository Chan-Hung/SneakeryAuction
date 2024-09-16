package com.hung.sneakery.service;

import com.hung.sneakery.dto.request.ResetPasswordRequest;
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

    /**
     * Reset Password
     *
     * @param request ResetPasswordRequest
     * @return BaseResponse
     */
    BaseResponse resetPassword(ResetPasswordRequest request);

    /**
     * Verify Phone Number
     *
     * @param phoneNumber String
     * @return BaseResponse
     */
    BaseResponse verifyPhoneNumber(String phoneNumber);
}
