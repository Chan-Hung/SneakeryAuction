package com.hung.sneakery.controller;

import com.hung.sneakery.dto.BidDTO;
import com.hung.sneakery.dto.request.BidCreateRequest;
import com.hung.sneakery.dto.request.BidPlaceRequest;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.exception.BidCreatingException;
import com.hung.sneakery.service.BidService;
import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@Api(tags = "Bid APIs")
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/", "http://localhost:3000", "https://sneakery.vercel.app/"})
@RequestMapping("/bids")
public class BidController {

    @Resource
    private BidService bidService;

    @PostMapping("/place")
    @PreAuthorize("hasRole('USER')")
    public BaseResponse placeBid(@RequestBody final BidPlaceRequest bidPlaceRequest) {
        return bidService.placeBid(bidPlaceRequest);
    }

    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public BaseResponse createBidProduct(
            @RequestPart final BidCreateRequest bidCreateRequest,
            @RequestPart(name = "thumbnail") final MultipartFile thumbnail,
            @RequestPart(name = "images") final List<MultipartFile> images) {
        try {
            return bidService.createBid(bidCreateRequest, thumbnail, images);
        } catch (RuntimeException | IOException | ParseException e) {
            throw new BidCreatingException("Can not create Bid product");
        }
    }

    @GetMapping("/get_uploaded_products")
    @PreAuthorize("hasRole('USER')")
    public List<BidDTO> getAll() {
        return bidService.getAllUploadedProduct();
    }
}
