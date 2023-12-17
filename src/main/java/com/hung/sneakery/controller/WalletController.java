package com.hung.sneakery.controller;

import com.hung.sneakery.dto.WalletDTO;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.entity.Wallet;
import com.hung.sneakery.service.WalletService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "Wallet APIs")
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/", "http://localhost:3000", "https://sneakery.vercel.app/"})
@RequestMapping("/wallet")
public class WalletController {

    @Resource
    private WalletService walletService;

    @PostMapping("/{userId}")
    public BaseResponse create(@PathVariable final Long userId) {
        return walletService.create(userId);
    }

    @GetMapping("/{userId}")
    public WalletDTO getOne(@PathVariable final Long userId) {
        return walletService.getOne(userId);
    }
}
