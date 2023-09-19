package com.hung.sneakery.dto.request;

import com.hung.sneakery.dto.OrderDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetRevenue {
    private Long revenueByAllOrders;
    private Long revenueByAuctionFee;
    private List<OrderDTO> orders;
}
