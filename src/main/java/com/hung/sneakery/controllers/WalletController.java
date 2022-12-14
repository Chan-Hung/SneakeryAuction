package com.hung.sneakery.controllers;

import com.hung.sneakery.data.models.dto.request.EmailRequest;
import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.remotes.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/", "http://localhost:3000", "https://sneakery.vercel.app/"})
@RequestMapping("/api/wallet")
public class WalletController {
    @Autowired
    WalletService walletService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse> create(@RequestBody EmailRequest email){
        try{
            return ResponseEntity
                    .ok(walletService.createWallet(email.getEmail()));
        }
        catch (RuntimeException e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse(false,
                            e.getMessage()));
        }
    }

}
