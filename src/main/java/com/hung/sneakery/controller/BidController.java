package com.hung.sneakery.controller;

import com.hung.sneakery.dto.BidDTO;
import com.hung.sneakery.dto.request.BidCreateRequest;
import com.hung.sneakery.dto.request.BidPlaceRequest;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.service.BidService;
import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = "Bid APIs")
@CrossOrigin(origins = {"http://localhost:3000", "https://sneakery.vercel.app/"})
@RequestMapping("/bids")
public class BidController {

    @Resource
    private BidService bidService;

    @PostMapping("/place")
    @PreAuthorize("hasRole('USER')")
    public BaseResponse placeBid(@Valid @RequestBody final BidPlaceRequest bidPlaceRequest) {
        return bidService.placeBid(bidPlaceRequest);
    }

    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public BaseResponse createBidProduct(@Valid @RequestBody final BidCreateRequest bidCreateRequest) {
        return bidService.createBid(bidCreateRequest);
    }

    @GetMapping("/uploaded-products")
    @PreAuthorize("hasRole('USER')")
    public List<BidDTO> getAll() {
        return bidService.getAllUploadedProduct();
    }
}
