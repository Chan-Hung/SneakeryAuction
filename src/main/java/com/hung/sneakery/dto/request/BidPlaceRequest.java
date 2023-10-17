package com.hung.sneakery.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class BidPlaceRequest {

    @JsonProperty("productId")
    @NotBlank
    private Long productId;

    @JsonProperty("amount")
    @NotBlank
    private Long amount;
}
