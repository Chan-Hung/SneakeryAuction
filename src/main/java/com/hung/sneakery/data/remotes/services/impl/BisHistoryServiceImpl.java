package com.hung.sneakery.data.remotes.services.impl;

import com.hung.sneakery.data.models.dto.BidHistoryDTO;
import com.hung.sneakery.data.models.dto.request.GetBidHistoryByUser;
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


    @Override
    public DataResponse<List<GetBidHistoryByUser>> getHistoryByUser(Long userId) {
        //One-To-One relation: bid<->product
        List<BidHistory> bidHistoryList = bidHistoryRepository.findByUser_Id(userId);
        if (bidHistoryList == null)
            throw new RuntimeException("User has not placed any bid");
        else {
            List<GetBidHistoryByUser> getBidHistoryByUsers = new ArrayList<>();
            for(BidHistory bidHistory : bidHistoryList){
                GetBidHistoryByUser getBidHistoryByUser = mapToGetBidHistoryByUser(bidHistory);
                getBidHistoryByUsers.add(getBidHistoryByUser);
            }
            return new DataResponse<>(getBidHistoryByUsers) ;
        }
    }

    private BidHistoryDTO mapToBidHistoryDTO(BidHistory bidHistory){
        BidHistoryDTO bidHistoryDTO = new BidHistoryDTO();

        bidHistoryDTO.setBidAmount(bidHistory.getPrice());
        bidHistoryDTO.setCreatedAt(bidHistory.getCreatedAt());
        bidHistoryDTO.setUserName(bidHistory.getUser().getUsername());

        return bidHistoryDTO;
    }

    private GetBidHistoryByUser mapToGetBidHistoryByUser(BidHistory bidHistory){
        GetBidHistoryByUser getBidHistoryByUser = new GetBidHistoryByUser();

        getBidHistoryByUser.setProductId(bidHistory.getBid().getId());
        getBidHistoryByUser.setCreatedAt(bidHistory.getCreatedAt());
        getBidHistoryByUser.setPrice(bidHistory.getPrice());
        return getBidHistoryByUser;
    }
}



