package com.hung.sneakery.controller;

import com.hung.sneakery.dto.BidHistoryDTO;
import com.hung.sneakery.dto.request.GetBidHistoryByUser;
import com.hung.sneakery.service.BidHistoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/", "http://localhost:3000", "https://sneakery.vercel.app/"})
@RequestMapping("/bid_history")
public class BidHistoryController {

    @Resource
    private BidHistoryService bidHistoryService;

    @GetMapping("/product/{productId}")
    public List<BidHistoryDTO> getOneByProduct(@PathVariable final Long productId) {
        return bidHistoryService.getHistoryByProduct(productId);
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public List<GetBidHistoryByUser> getOneByUser() {
        return bidHistoryService.getHistoryByUser();
    }
}
