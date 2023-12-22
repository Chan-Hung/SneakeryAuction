package com.hung.sneakery.service;

import com.hung.sneakery.dto.BidDTO;
import com.hung.sneakery.dto.request.BidCreateRequest;
import com.hung.sneakery.dto.request.BidPlaceRequest;
import com.hung.sneakery.dto.response.BaseResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
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
     * @param thumbnail MultipartFile
     * @param images    List<MultipartFile>
     * @return BaseResponse
     * @throws IOException    IOException
     * @throws ParseException ParseException
     */
    BaseResponse createBid(BidCreateRequest request, MultipartFile thumbnail, List<MultipartFile> images) throws IOException, ParseException;

    /**
     * Get All Uploaded Products
     *
     * @return List<BidDTO>
     */
    List<BidDTO> getAllUploadedProduct();
}
