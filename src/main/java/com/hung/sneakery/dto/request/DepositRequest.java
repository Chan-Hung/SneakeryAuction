package com.hung.sneakery.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class DepositRequest {

    @JsonProperty("userId")
    @NotBlank
    private Long userId;

    @JsonProperty("amount")
    @NotBlank
    private Long amount;
}
