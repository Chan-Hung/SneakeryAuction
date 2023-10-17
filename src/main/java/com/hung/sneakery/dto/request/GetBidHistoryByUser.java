package com.hung.sneakery.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hung.sneakery.dto.ProductDTO;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
public class GetBidHistoryByUser {

    @JsonProperty("amount")
    @NotBlank
    private Long amount;

    @JsonProperty("createdAt")
    @NotBlank
    private LocalDateTime createdAt;

    @JsonProperty("product")
    @NotBlank
    private ProductDTO product;
}
