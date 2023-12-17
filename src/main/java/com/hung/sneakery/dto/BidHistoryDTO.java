package com.hung.sneakery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BidHistoryDTO {

    @JsonProperty("bidHistoryId")
    private Long bidHistoryId;

    @JsonProperty("bidAmount")
    private Long bidAmount;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("userName")
    private String userName;
}
