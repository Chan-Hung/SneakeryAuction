package com.hung.sneakery.controller;

import com.hung.sneakery.dto.request.EmailRequest;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/", "http://localhost:3000", "https://sneakery.vercel.app/"})
@RequestMapping("/wallet")
public class WalletController {

    @Resource
    WalletService walletService;

    @PostMapping()
    public ResponseEntity<BaseResponse> create(@RequestBody EmailRequest email) {
        try {
            return ResponseEntity
                    .ok(walletService.createWallet(email.getEmail()));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse(false,
                            e.getMessage()));
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<BaseResponse> getOne(@PathVariable Long userId) {
        try {
            return ResponseEntity
                    .ok(walletService.getOne(userId));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse(false,
                            e.getMessage()));
        }
    }
}
