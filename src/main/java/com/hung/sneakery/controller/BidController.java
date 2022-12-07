package com.hung.sneakery.controller;

import com.hung.sneakery.payload.request.BidCreateRequest;
import com.hung.sneakery.payload.request.BidPlaceRequest;
import com.hung.sneakery.payload.response.BaseResponse;
import com.hung.sneakery.services.BidService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> placeBid(@RequestBody BidPlaceRequest bidPlaceRequest){
        try{
               return ResponseEntity
                       .ok(bidService.placeBid(bidPlaceRequest));
        }
        catch (RuntimeException e){
            return ResponseEntity
                    .status(500)
                    .body(new BaseResponse(false,
                            e.getMessage()));
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
        public ResponseEntity<?> createBidProduct(@RequestPart BidCreateRequest bidCreateRequest, @RequestPart(name = "thumbnail") MultipartFile thumbnail, @RequestPart(name = "images") List<MultipartFile> images){
        try{
            return ResponseEntity
                    .ok(bidService.createBid(bidCreateRequest, thumbnail, images));
        }
        catch (RuntimeException | IOException | ParseException e){
            return ResponseEntity
                    .status(500)
                    .body(new BaseResponse(false,
                            e.getMessage()));
        }
    }
}
