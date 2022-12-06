package com.hung.sneakery.services;

import com.hung.sneakery.payload.request.BidCreateRequest;
import com.hung.sneakery.payload.request.BidPlaceRequest;
import com.hung.sneakery.payload.response.BaseResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface BidService {
    BaseResponse placeBid(BidPlaceRequest bidPlaceRequest);

    BaseResponse createBid(BidCreateRequest bidCreateRequest, MultipartFile thumbnail, List<MultipartFile> images) throws IOException, ParseException;
}
