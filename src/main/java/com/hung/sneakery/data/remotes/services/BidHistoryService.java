package com.hung.sneakery.data.remotes.services;

import com.hung.sneakery.data.models.dto.BidHistoryDTO;
import com.hung.sneakery.data.models.dto.request.GetBidHistoryByUser;
import com.hung.sneakery.data.models.dto.response.DataResponse;

import java.util.List;

public interface BidHistoryService {

    DataResponse<List<BidHistoryDTO>> getHistoryByProduct(Long productId);

    DataResponse<List<GetBidHistoryByUser>> getHistoryByUser();
}
