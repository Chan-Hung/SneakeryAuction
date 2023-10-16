package com.hung.sneakery.service;

import com.hung.sneakery.dto.BidHistoryDTO;
import com.hung.sneakery.dto.request.GetBidHistoryByUser;

import java.util.List;

public interface BidHistoryService {

    /**
     * Get Bid History Of Product
     *
     * @param productId Long
     * @return List<BidHistoryDTO>
     */
    List<BidHistoryDTO> getHistoryByProduct(Long productId);

    /**
     * Git Bid History Of User
     *
     * @return List<GetBidHistoryByUser>
     */
    List<GetBidHistoryByUser> getHistoryByUser();
}
