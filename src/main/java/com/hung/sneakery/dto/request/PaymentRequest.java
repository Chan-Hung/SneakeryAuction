package com.hung.sneakery.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class PaymentRequest {

    @JsonProperty("purpose")
    @NotNull
    private String purpose;

    @JsonProperty("amount")
    @NotNull
    private Long amount;
}
