package com.hung.sneakery.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class BidPlaceRequest {

    @JsonProperty("productId")
    @NotNull
    private Long productId;

    @JsonProperty("amount")
    @NotNull
    private Long amount;
}
