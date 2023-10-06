package com.hung.sneakery.service;

import com.hung.sneakery.dto.BidHistoryDTO;
import com.hung.sneakery.dto.request.GetBidHistoryByUser;
import com.hung.sneakery.dto.response.DataResponse;

import java.util.List;

public interface BidHistoryService {

    DataResponse<List<BidHistoryDTO>> getHistoryByProduct(Long productId);

    DataResponse<List<GetBidHistoryByUser>> getHistoryByUser();
}
