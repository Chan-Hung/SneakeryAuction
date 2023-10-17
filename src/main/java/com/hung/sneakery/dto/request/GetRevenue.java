package com.hung.sneakery.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.hung.sneakery.dto.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetRevenue {

    @JsonProperty("revenueByAllOrders")
    private Long revenueByAllOrders;

    @JsonSetter("revenueByAuctionFee")
    private Long revenueByAuctionFee;

    @JsonProperty("orders")
    private List<OrderDTO> orders;
}
