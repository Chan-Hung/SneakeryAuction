package com.hung.sneakery.converter;

import com.hung.sneakery.dto.BidDTO;
import com.hung.sneakery.dto.BidDetailDTO;
import com.hung.sneakery.entity.Bid;

import java.util.List;

public interface BidConverter {

    BidDTO convertToBidDTO(Bid bid);

    BidDetailDTO convertToBidDetailDTO(Bid bid);

    List<BidDTO> convertToBidDTOList(List<Bid> bids);
}
