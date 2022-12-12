package com.hung.sneakery.data.remotes.services.impl;

import com.hung.sneakery.data.models.dto.BidHistoryDTO;
import com.hung.sneakery.data.models.dto.response.DataResponse;
import com.hung.sneakery.data.models.entities.BidHistory;
import com.hung.sneakery.data.remotes.repositories.BidHistoryRepository;
import com.hung.sneakery.data.remotes.services.BidHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BisHistoryServiceImpl implements BidHistoryService {
    @Autowired
    BidHistoryRepository bidHistoryRepository;

    @Override
    public DataResponse<List<BidHistoryDTO>> getHistoryByProduct(Long productId) {
        //One-To-One relation: bid<->product
        List<BidHistory> bidHistoryList = bidHistoryRepository.findByBid_Id(productId);
        if (bidHistoryList == null)
            throw new RuntimeException("Bid History not found");
        else {
            List<BidHistoryDTO> bidHistoryDTOList = new ArrayList<>();
            for(BidHistory bidHistory : bidHistoryList){
                BidHistoryDTO bidHistoryDTO = mapToBidHistoryDTO(bidHistory);
                bidHistoryDTOList.add(bidHistoryDTO);
            }
            return new DataResponse<>(bidHistoryDTOList) ;
        }
    }

    private BidHistoryDTO mapToBidHistoryDTO(BidHistory bidHistory){
        BidHistoryDTO bidHistoryDTO = new BidHistoryDTO();

        bidHistoryDTO.setBidAmount(bidHistory.getPrice());
        bidHistoryDTO.setCreatedAt(bidHistory.getCreatedAt());
        bidHistoryDTO.setUserName(bidHistory.getUser().getUsername());

        return bidHistoryDTO;
    }
}
