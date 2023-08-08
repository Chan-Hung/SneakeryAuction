package com.hung.sneakery.controllers;

import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.remotes.services.BidHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/", "http://localhost:3000", "https://sneakery.vercel.app/"})
@RequestMapping("/api/bid_history")
public class BidHistoryController {

    @Resource
    BidHistoryService bidHistoryService;

    @GetMapping("/product/{productId}")
    public ResponseEntity<BaseResponse> getOneByProduct(@PathVariable Long productId) {
        try {
            return ResponseEntity
                    .ok(bidHistoryService.getHistoryByProduct(productId));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse(false,
                            e.getMessage()));
        }
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse> getOneByUser() {
        try {
            return ResponseEntity
                    .ok(bidHistoryService.getHistoryByUser());
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse(false,
                            e.getMessage()));
        }
    }
}
