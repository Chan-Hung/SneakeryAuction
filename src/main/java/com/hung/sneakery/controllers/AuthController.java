package com.hung.sneakery.controllers;


import com.hung.sneakery.data.models.dto.request.EmailRequest;
import com.hung.sneakery.data.models.dto.request.SignInRequest;
import com.hung.sneakery.data.models.dto.request.SignUpRequest;
import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.remotes.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/", "http://localhost:3000", "https://sneakery.vercel.app/"})
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<BaseResponse> signIn(@Valid @RequestBody SignInRequest signinRequest) {
        try {
            return ResponseEntity
                    .ok(authService.signIn(signinRequest));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse(false, e.getMessage()));
        }
    }

    @PostMapping("/checkemail")
    public ResponseEntity<BaseResponse> checkEmail(@Valid @RequestBody EmailRequest emailRequest) {
        return ResponseEntity
                .ok(authService.checkEmail(emailRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) throws MessagingException, UnsupportedEncodingException {
        try {
            return ResponseEntity
                    .ok(authService.signUp(signUpRequest));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse(false, e.getMessage()));
        }
    }
}
