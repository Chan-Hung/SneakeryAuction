package com.hung.sneakery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hung.sneakery.enums.EPaymentType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TransactionDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("amount")
    private Long amount;

    @JsonProperty("type")
    private EPaymentType type;

    @JsonProperty("bid")
    private BidDTO bid;
}
