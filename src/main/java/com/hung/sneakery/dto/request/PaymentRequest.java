package com.hung.sneakery.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class PaymentRequest {

    @JsonProperty("userId")
    @NotNull
    private Long userId;

    @JsonProperty("amount")
    @NotNull
    private Long amount;
}
