package com.hung.sneakery.service;

import com.hung.sneakery.dto.BidDTO;
import com.hung.sneakery.dto.BidDetailDTO;
import com.hung.sneakery.dto.request.BidCreateRequest;
import com.hung.sneakery.dto.request.BidPlaceRequest;
import com.hung.sneakery.dto.response.BaseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BidService {

    /**
     * Place A Bid
     *
     * @param request BidPlaceRequest
     * @return BaseResponse
     */
    BaseResponse placeBid(BidPlaceRequest request);

    /**
     * Create A Bid
     *
     * @param request BidCreateRequest
     * @return BaseResponse
     */
    BidDetailDTO createBid(BidCreateRequest request);

    /**
     * Get All Uploaded Products
     *
     * @return List<BidDTO>
     */
    List<BidDTO> getUploadedProduct();

    /**
     * Get Winning Bids
     *
     * @return List<BidDTO>
     */
    Page<BidDTO> getWinningBids(Pageable pageable);
}
