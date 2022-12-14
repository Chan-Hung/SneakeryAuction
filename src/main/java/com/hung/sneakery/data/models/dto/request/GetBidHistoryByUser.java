package com.hung.sneakery.data.models.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GetBidHistoryByUser {
    private Long productId;
    private Long price;
    private LocalDateTime createdAt;
}
