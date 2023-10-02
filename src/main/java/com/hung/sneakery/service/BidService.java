package com.hung.sneakery.service;

import com.hung.sneakery.data.models.dto.BidDTO;
import com.hung.sneakery.data.models.dto.request.BidCreateRequest;
import com.hung.sneakery.data.models.dto.request.BidPlaceRequest;
import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.models.dto.response.DataResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface BidService {

    BaseResponse placeBid(BidPlaceRequest bidPlaceRequest);

    BaseResponse createBid(BidCreateRequest bidCreateRequest, MultipartFile thumbnail, List<MultipartFile> images) throws IOException, ParseException;

    DataResponse<List<BidDTO>> getAllUploadedProduct();
}
