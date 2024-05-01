package com.hung.sneakery.service.impl;

import com.hung.sneakery.converter.BidHistoryConverter;
import com.hung.sneakery.converter.ProductConverter;
import com.hung.sneakery.dto.BidHistoryDTO;
import com.hung.sneakery.dto.request.GetBidHistoryByUser;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.entity.BidHistory;
import com.hung.sneakery.entity.User;
import com.hung.sneakery.enums.EBidStatus;
import com.hung.sneakery.exception.NotFoundException;
import com.hung.sneakery.repository.BidHistoryRepository;
import com.hung.sneakery.service.BidHistoryService;
import com.hung.sneakery.utils.SneakeryUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BidHistoryServiceImpl implements BidHistoryService {

    @Resource
    private BidHistoryRepository bidHistoryRepository;

    @Resource
    private ProductConverter productConverter;

    @Resource
    private BidHistoryConverter bidHistoryConverter;

    @Resource
    private SneakeryUtil sneakeryUtil;

    @Override
    public List<BidHistoryDTO> getHistoryByProduct(final Long productId) {
        List<BidHistory> bidHistories = bidHistoryRepository.findByBid_IdOrderByCreatedDateDesc(productId);
        if (Objects.isNull(bidHistories)) {
            throw new NotFoundException("Bid History not found");
        }
        return bidHistoryConverter.convertToBidHistoryDTOList(bidHistories);
    }

    @Override
    public List<GetBidHistoryByUser> getHistoryByUser() {
        User user = sneakeryUtil.getCurrentUser();

        List<BidHistory> bidHistoryList = bidHistoryRepository.findByUser_IdOrderByCreatedDateDesc(user.getId());
        List<GetBidHistoryByUser> getBidHistoryByUsers = new ArrayList<>();
        for (BidHistory bidHistory : bidHistoryList) {
            GetBidHistoryByUser getBidHistoryByUser = mapToGetBidHistoryByUser(bidHistory);
            getBidHistoryByUsers.add(getBidHistoryByUser);
        }
        return getBidHistoryByUsers;
    }

    @Override
    public BaseResponse delete(final Long id) {
        BidHistory bidHistory = bidHistoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Bid History not found"));
        bidHistory.setStatus(EBidStatus.REMOVE);
        bidHistoryRepository.save(bidHistory);
        return new BaseResponse("Rút lại lần ra giá thành công");
    }

    private GetBidHistoryByUser mapToGetBidHistoryByUser(final BidHistory bidHistory) {
        return GetBidHistoryByUser.builder()
                .bidHistoryId(bidHistory.getId())
                .status(bidHistory.getStatus().toString())
                .createdAt(bidHistory.getCreatedDate())
                .amount(bidHistory.getActualPrice())
                .product(productConverter.convertToProductDTO(bidHistory.getBid().getProduct()))
                .build();
    }
}



