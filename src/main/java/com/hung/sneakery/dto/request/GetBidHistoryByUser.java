package com.hung.sneakery.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hung.sneakery.dto.ProductDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GetBidHistoryByUser {

    @JsonProperty("bidHistoryId")
    private Long bidHistoryId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("amount")
    private Long amount;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("product")
    private ProductDTO product;
}