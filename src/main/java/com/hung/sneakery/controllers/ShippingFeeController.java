package com.hung.sneakery.controllers;

import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.remotes.services.ShippingFeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/", "http://localhost:3000", "https://sneakery.vercel.app/"})
@RequestMapping("/shipping_fee")
public class ShippingFeeController {

    @Resource
    ShippingFeeService shippingFeeService;

    @GetMapping()
    public ResponseEntity<BaseResponse> getOne(
            @RequestParam(name = "originDistrict") String originDistrict,
            @RequestParam(name = "destinationDistrict") String destinationDistrict) {
        try {
            return ResponseEntity
                    .ok(shippingFeeService.getOne(originDistrict, destinationDistrict));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse(false, e.getMessage()));
        }
    }
}
