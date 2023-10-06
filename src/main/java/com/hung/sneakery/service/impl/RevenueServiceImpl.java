package com.hung.sneakery.service.impl;

import com.hung.sneakery.dto.OrderDTO;
import com.hung.sneakery.dto.request.GetRevenue;
import com.hung.sneakery.dto.response.DataResponse;
import com.hung.sneakery.entities.Order;
import com.hung.sneakery.repository.BidRepository;
import com.hung.sneakery.repository.OrderRepository;
import com.hung.sneakery.service.RevenueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RevenueServiceImpl implements RevenueService {

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private BidRepository bidRepository;

    @Override
    public DataResponse<GetRevenue> getRevenue() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for(Order order: orders){
            OrderDTO orderDTO = OrderServiceImpl.mapToOrderDTO(order);
            orderDTOs.add(orderDTO);
        }
        Long allRevenue = bidRepository.getRevenueByAllOrders();
        GetRevenue getRevenue = new GetRevenue();
        getRevenue.setOrders(orderDTOs);
        getRevenue.setRevenueByAllOrders(allRevenue);
        getRevenue.setRevenueByAuctionFee(allRevenue*10/100);

        return new DataResponse<>(getRevenue);
    }
}
