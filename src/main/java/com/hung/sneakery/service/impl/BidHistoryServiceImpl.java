package com.hung.sneakery.service.impl;

import com.hung.sneakery.converter.ProductConverter;
import com.hung.sneakery.dto.BidHistoryDTO;
import com.hung.sneakery.dto.request.GetBidHistoryByUser;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.entity.BidHistory;
import com.hung.sneakery.entity.User;
import com.hung.sneakery.enums.EBidStatus;
import com.hung.sneakery.exception.NotFoundException;
import com.hung.sneakery.repository.BidHistoryRepository;
import com.hung.sneakery.repository.UserRepository;
import com.hung.sneakery.service.BidHistoryService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class BidHistoryServiceImpl implements BidHistoryService {

    @Resource
    private BidHistoryRepository bidHistoryRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private ProductConverter productConverter;

    @Override
    public List<BidHistoryDTO> getHistoryByProduct(Long productId) {
        //One-To-One relation: bid<->product
        List<BidHistory> bidHistoryList = bidHistoryRepository.findByBid_IdOrderByCreatedDateDesc(productId);
        if (bidHistoryList == null) {
            throw new NotFoundException("Bid History not found");
        }
        List<BidHistoryDTO> bidHistoryDTOList = new ArrayList<>();
        for (BidHistory bidHistory : bidHistoryList) {
            BidHistoryDTO bidHistoryDTO = mapToBidHistoryDTO(bidHistory);
            bidHistoryDTOList.add(bidHistoryDTO);
        }
        return bidHistoryDTOList;
    }


    @Override
    public List<GetBidHistoryByUser> getHistoryByUser() {
        //One-To-One relation: bid<->product
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);

        List<BidHistory> bidHistoryList = bidHistoryRepository.findByUser_Id(user.getId());
        if (bidHistoryList == null) {
            throw new NotFoundException("User has not placed any bid");
        }
        List<GetBidHistoryByUser> getBidHistoryByUsers = new ArrayList<>();
        for (BidHistory bidHistory : bidHistoryList) {
            GetBidHistoryByUser getBidHistoryByUser = mapToGetBidHistoryByUser(bidHistory);
            getBidHistoryByUsers.add(getBidHistoryByUser);
        }
        return getBidHistoryByUsers;
    }

    @Override
    public BaseResponse delete(Long bidHistoryId) {
        BidHistory bidHistory = bidHistoryRepository.findById(bidHistoryId)
                .orElseThrow(() -> new NotFoundException("Bid History not found"));
        bidHistory.setStatus(EBidStatus.REMOVE);
        bidHistoryRepository.save(bidHistory);
        return new BaseResponse("Rút lại lần ra giá thành công");
    }

    private BidHistoryDTO mapToBidHistoryDTO(BidHistory bidHistory) {
        return BidHistoryDTO.builder()
                .bidHistoryId(bidHistory.getId())
                .bidAmount(bidHistory.getPrice())
                .createdAt(bidHistory.getCreatedDate())
                .userName(bidHistory.getUser().getUsername()).build();
    }

    private GetBidHistoryByUser mapToGetBidHistoryByUser(BidHistory bidHistory) {
        return GetBidHistoryByUser.builder()
                .createdAt(bidHistory.getCreatedDate())
                .amount(bidHistory.getPrice())
                .product(productConverter.convertToProductDTO(bidHistory.getBid().getProduct()))
                .build();
    }
}



