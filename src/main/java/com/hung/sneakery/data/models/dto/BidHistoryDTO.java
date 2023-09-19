package com.hung.sneakery.data.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BidHistoryDTO {
    private Long bidAmount;

    private LocalDateTime createdAt;

    private String userName;
}
