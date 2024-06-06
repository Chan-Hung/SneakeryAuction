package com.hung.sneakery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FeedbackDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("rating")
    private int rating;

    @JsonProperty("feedbackText")
    private String feedbackText;

    @JsonProperty("product")
    private ProductDTO productDTO;

    @JsonProperty("winner")
    private String winner;

    @JsonProperty("seller")
    private String seller;
}
