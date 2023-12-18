package com.hung.sneakery.converter.impl;

import com.hung.sneakery.converter.BidHistoryConverter;
import com.hung.sneakery.dto.BidHistoryDTO;
import com.hung.sneakery.entity.BidHistory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BidHistoryConverterImpl implements BidHistoryConverter {

    @Override
    public BidHistoryDTO convertToBidHistoryDTO(BidHistory bidHistory) {
        return BidHistoryDTO.builder()
                .bidHistoryId(bidHistory.getId())
                .bidAmount(bidHistory.getPrice())
                .status(bidHistory.getStatus().toString())
                .createdAt(bidHistory.getCreatedDate())
                .userName(bidHistory.getUser().getUsername())
                .build();
    }

    @Override
    public List<BidHistoryDTO> convertToBidHistoryDTOList(List<BidHistory> bidHistories) {
        return Optional.ofNullable(bidHistories).orElse(Collections.emptyList())
                .stream().map(this::convertToBidHistoryDTO).collect(Collectors.toList());
    }
}
