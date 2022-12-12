package com.hung.sneakery.data.remotes.services.impl;

import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.models.entities.BidHistory;
import com.hung.sneakery.data.remotes.repositories.BidHistoryRepository;
import com.hung.sneakery.data.remotes.services.BidHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BisHistoryServiceImpl implements BidHistoryService {
    @Autowired
    BidHistoryRepository bidHistoryRepository;

    @Override
    public BaseResponse getHistoryByProduct(Long productId) {
        //One-To-One relation: bid<->product
        List<BidHistory> bidHistoryList = bidHistoryRepository.findByBid_Id(productId);
        System.out.println("GET");
        return null;
    }
}
