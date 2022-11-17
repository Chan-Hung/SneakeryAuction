package com.hung.sneakery.controller;

import com.hung.sneakery.payload.request.BidPlaceRequest;
import com.hung.sneakery.payload.response.BaseResponse;
import com.hung.sneakery.services.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
}
