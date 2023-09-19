package com.hung.sneakery.service;

import com.hung.sneakery.dto.request.SignInRequest;
import com.hung.sneakery.dto.request.SignUpRequest;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.dto.response.DataResponse;
import com.hung.sneakery.dto.response.JwtResponse;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface AuthService {

    DataResponse<JwtResponse> signIn(SignInRequest signInRequest);

    BaseResponse signUp(SignUpRequest signUpRequest) throws MessagingException, UnsupportedEncodingException;

    BaseResponse checkEmail(String email);

    BaseResponse verify(String verificationCode);
}
