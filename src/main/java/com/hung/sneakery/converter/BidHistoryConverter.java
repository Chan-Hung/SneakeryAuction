package com.hung.sneakery.converter;

import com.hung.sneakery.dto.BidHistoryDTO;
import com.hung.sneakery.entity.BidHistory;

public interface BidHistoryConverter {

    BidHistoryDTO convertBidHistoryToBidHistoryDTO(BidHistory bidHistory); 
}
