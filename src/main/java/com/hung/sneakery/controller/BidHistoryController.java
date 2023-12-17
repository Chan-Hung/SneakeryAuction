package com.hung.sneakery.controller;

import com.hung.sneakery.dto.BidHistoryDTO;
import com.hung.sneakery.dto.request.GetBidHistoryByUser;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.service.BidHistoryService;
import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "Bid History APIs")
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/", "http://localhost:3000", "https://sneakery.vercel.app/"})
@RequestMapping("/bid-history")
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

    @DeleteMapping("/{bidHistoryId}")
    public BaseResponse delete(@PathVariable final Long bidHistoryId) {
        return bidHistoryService.delete(bidHistoryId);
    }
}
