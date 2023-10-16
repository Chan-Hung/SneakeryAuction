package com.hung.sneakery.controller;

import com.hung.sneakery.dto.request.EmailRequest;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.entity.Wallet;
import com.hung.sneakery.service.WalletService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/", "http://localhost:3000", "https://sneakery.vercel.app/"})
@RequestMapping("/wallet")
public class WalletController {

    @Resource
    private WalletService walletService;

    @PostMapping()
    public BaseResponse create(@RequestBody final EmailRequest email) {
        return walletService.create(email.getEmail());
    }

    @GetMapping("/{userId}")
    public Wallet getOne(@PathVariable final Long userId) {
        return walletService.getOne(userId);
    }
}
