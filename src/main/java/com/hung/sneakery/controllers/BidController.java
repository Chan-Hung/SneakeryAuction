package com.hung.sneakery.controllers;

import com.hung.sneakery.data.models.dto.request.BidCreateRequest;
import com.hung.sneakery.data.models.dto.request.BidPlaceRequest;
import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.remotes.services.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/","http://localhost:3000"})
@RequestMapping("/api/bids")
public class BidController {
    @Autowired
    BidService bidService;

    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse> placeBid(@RequestBody BidPlaceRequest bidPlaceRequest){
        try{
               return ResponseEntity
                       .ok(bidService.placeBid(bidPlaceRequest));
        }
        catch (RuntimeException e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse(false,
                            e.getMessage()));
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
        public ResponseEntity<BaseResponse> createBidProduct(
                @RequestPart BidCreateRequest bidCreateRequest,
                @RequestPart(name = "thumbnail") MultipartFile thumbnail,
                @RequestPart(name = "images") List<MultipartFile> images){
        try{
            return ResponseEntity
                    .ok(bidService.createBid(bidCreateRequest, thumbnail, images));
        }
        catch (RuntimeException | IOException | ParseException e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse(false,
                            e.getMessage()));
        }
    }
}
