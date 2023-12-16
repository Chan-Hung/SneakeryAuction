package com.hung.sneakery.service;

import com.hung.sneakery.dto.request.SignInRequest;
import com.hung.sneakery.dto.request.SignUpRequest;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.dto.response.JwtResponse;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface AuthService {

    /**
     * Sign In
     *
     * @param signInRequest SignInRequest
     * @return JwtResponse
     */
    JwtResponse signIn(SignInRequest signInRequest);

    /**
     * Sign Up
     *
     * @param signUpRequest SignUpRequest
     * @return BaseResponse
     * @throws MessagingException           MessagingException
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     */
    BaseResponse signUp(SignUpRequest signUpRequest) throws MessagingException, UnsupportedEncodingException;

    /**
     * Check User's Email
     *
     * @param email String
     * @return BaseResponse
     */
    BaseResponse checkEmail(String email);

    /**
     * Verify User Whether Is Activated Or Not
     *
     * @param verificationCode String
     * @return BaseResponse
     */
    BaseResponse verify(String verificationCode);
}
