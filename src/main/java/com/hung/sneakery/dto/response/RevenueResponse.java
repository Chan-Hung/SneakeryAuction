package com.hung.sneakery.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.hung.sneakery.dto.OrderDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RevenueResponse {

    @JsonProperty("revenueByAllOrders")
    private Long revenueByAllOrders;

    @JsonSetter("revenueByAuctionFee")
    private Long revenueByAuctionFee;

    @JsonProperty("orders")
    private List<OrderDTO> orders;
}
