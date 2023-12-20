package com.hung.sneakery.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hung.sneakery.enums.EOrderStatus;
import lombok.Data;

@Data
public class OrderRequest {

    @JsonProperty("status")
    private EOrderStatus status;
}
