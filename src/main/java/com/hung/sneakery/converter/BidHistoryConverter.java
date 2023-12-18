package com.hung.sneakery.converter;

import com.hung.sneakery.dto.BidHistoryDTO;
import com.hung.sneakery.entity.BidHistory;

import java.util.List;

public interface BidHistoryConverter {

    BidHistoryDTO convertToBidHistoryDTO(BidHistory bidHistory);

    List<BidHistoryDTO> convertToBidHistoryDTOList(List<BidHistory> bidHistories);
}
