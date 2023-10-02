package com.hung.sneakery.controller;


import com.hung.sneakery.data.models.dto.request.SignInRequest;
import com.hung.sneakery.data.models.dto.request.SignUpRequest;
import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/", "http://localhost:3000", "https://sneakery.vercel.app/", "https://aunction-react-js.vercel.app/"})
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private AuthService authService;

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

    @GetMapping("/check-email")
    public ResponseEntity<BaseResponse> checkEmail(@RequestParam String email) {
        return ResponseEntity
                .ok(authService.checkEmail(email));
    }

    @GetMapping("/verify/{code}")
    public ResponseEntity<BaseResponse> verify(@PathVariable String code) {
        try {
            return ResponseEntity
                    .ok(authService.verify(code));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse(false,
                            e.getMessage()));
        }
    }
}
