package com.hung.sneakery.controller;

import com.hung.sneakery.entity.ShippingFee;
import com.hung.sneakery.service.ShippingFeeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/", "http://localhost:3000", "https://sneakery.vercel.app/"})
@RequestMapping("/shipping_fee")
public class ShippingFeeController {

    @Resource
    private ShippingFeeService shippingFeeService;

    @GetMapping()
    public ShippingFee getOne(
            @RequestParam(name = "originDistrict") final String originDistrict,
            @RequestParam(name = "destinationDistrict") final String destinationDistrict) {
        return shippingFeeService.getOne(originDistrict, destinationDistrict);
    }
}
