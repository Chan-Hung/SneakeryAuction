package com.hung.sneakery.service.impl;

import com.hung.sneakery.converter.OrderConverter;
import com.hung.sneakery.dto.OrderDTO;
import com.hung.sneakery.dto.response.RevenueResponse;
import com.hung.sneakery.entity.Order;
import com.hung.sneakery.repository.BidRepository;
import com.hung.sneakery.repository.OrderRepository;
import com.hung.sneakery.service.RevenueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RevenueServiceImpl implements RevenueService {

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private BidRepository bidRepository;

    @Resource
    private OrderConverter orderConverter;

    @Override
    public RevenueResponse getRevenue() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOs = orderConverter.convertToOrderDTOList(orders);

        Long allRevenue = bidRepository.getRevenueByAllOrders();
        return RevenueResponse.builder()
                .orders(orderDTOs)
                .revenueByAllOrders(allRevenue)
                .revenueByAuctionFee(allRevenue * 10 / 100)
                .build();
    }
}
