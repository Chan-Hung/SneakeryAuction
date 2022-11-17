package com.hung.sneakery.services;

import com.hung.sneakery.model.BidHistory;
import com.hung.sneakery.payload.request.BidPlaceRequest;
import com.hung.sneakery.payload.response.DataResponse;

public interface BidService {
    DataResponse<BidHistory> placeBid(BidPlaceRequest bidPlaceRequest);
}
