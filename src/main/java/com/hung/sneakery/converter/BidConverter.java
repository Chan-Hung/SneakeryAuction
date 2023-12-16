package com.hung.sneakery.converter;

import com.hung.sneakery.dto.BidDTO;
import com.hung.sneakery.entity.Bid;

import java.util.List;

public interface BidConverter {

    BidDTO convertToBidDTO(Bid bid);

    List<BidDTO> convertToBidDTOList(List<Bid> bids);
}
