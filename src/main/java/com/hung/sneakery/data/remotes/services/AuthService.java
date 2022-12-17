package com.hung.sneakery.data.remotes.services;

import com.hung.sneakery.data.models.dto.request.EmailRequest;
import com.hung.sneakery.data.models.dto.request.SignInRequest;
import com.hung.sneakery.data.models.dto.request.SignUpRequest;
import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.models.dto.response.DataResponse;
import com.hung.sneakery.data.models.dto.response.JwtResponse;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface AuthService {
    DataResponse<JwtResponse> signIn(SignInRequest signInRequest);
    BaseResponse signUp(SignUpRequest signUpRequest) throws MessagingException, UnsupportedEncodingException;
    BaseResponse checkEmail(EmailRequest emailRequest);
    BaseResponse verify(String verificationCode);

}
