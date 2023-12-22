package com.hung.sneakery.controller;

import com.hung.sneakery.dto.WalletDTO;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.service.WalletService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "Wallet APIs")
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/", "http://localhost:3000", "https://sneakery.vercel.app/"})
@RequestMapping("/wallet")
public class WalletController {

    @Resource
    private WalletService walletService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public Page<WalletDTO> getAll(Pageable pageable) {
        return walletService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public WalletDTO getOne(@PathVariable final Long id) {
        return walletService.getOne(id);
    }

    @PostMapping("/{id}")
    public BaseResponse create(@PathVariable final Long id) {
        return walletService.create(id);
    }
}
