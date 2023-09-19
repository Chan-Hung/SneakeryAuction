package com.hung.sneakery.data.remotes.services.impl;

import com.hung.sneakery.data.models.dto.OrderDTO;
import com.hung.sneakery.data.models.dto.request.GetRevenue;
import com.hung.sneakery.data.models.dto.response.DataResponse;
import com.hung.sneakery.data.models.entities.Order;
import com.hung.sneakery.data.remotes.repositories.BidRepository;
import com.hung.sneakery.data.remotes.repositories.OrderRepository;
import com.hung.sneakery.data.remotes.services.RevenueService;
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
