package com.hung.sneakery.service;

import com.hung.sneakery.dto.BidDTO;
import com.hung.sneakery.dto.request.BidCreateRequest;
import com.hung.sneakery.dto.request.BidPlaceRequest;
import com.hung.sneakery.dto.response.BaseResponse;

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
     * @param request   BidCreateRequest
     * @return BaseResponse
     */
    BaseResponse createBid(BidCreateRequest request);

    /**
     * Get All Uploaded Products
     *
     * @return List<BidDTO>
     */
    List<BidDTO> getAllUploadedProduct();
}
