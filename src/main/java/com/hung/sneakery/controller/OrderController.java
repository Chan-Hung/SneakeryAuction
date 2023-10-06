package com.hung.sneakery.controller;

import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/", "http://localhost:3000", "https://sneakery.vercel.app/"})
@RequestMapping("/orders")
public class OrderController {

    @Resource
    OrderService orderService;

    @GetMapping()
    public ResponseEntity<BaseResponse> getAllByUser() {
        try {
            return ResponseEntity
                    .ok(orderService.getAllByUser());
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse(false, e.getMessage()));
        }
    }

}
