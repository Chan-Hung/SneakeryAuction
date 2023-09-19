package com.hung.sneakery.data.models.dto.request;

import com.hung.sneakery.data.models.dto.OrderDTO;
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
