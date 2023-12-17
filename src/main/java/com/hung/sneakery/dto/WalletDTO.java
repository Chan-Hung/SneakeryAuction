package com.hung.sneakery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WalletDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("balance")
    private Long balance;
}
