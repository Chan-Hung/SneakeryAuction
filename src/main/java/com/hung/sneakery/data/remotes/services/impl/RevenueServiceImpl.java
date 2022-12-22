package com.hung.sneakery.data.remotes.services.impl;

import com.hung.sneakery.data.models.dto.OrderDTO;
import com.hung.sneakery.data.models.dto.response.DataResponse;
import com.hung.sneakery.data.models.entities.Order;
import com.hung.sneakery.data.remotes.repositories.OrderRepository;
import com.hung.sneakery.data.remotes.services.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RevenueServiceImpl implements RevenueService {
    @Autowired
    OrderRepository orderRepository;

    @Override
    public DataResponse<List<OrderDTO>> getAllByAdmin() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for(Order order: orders){
            OrderDTO orderDTO = OrderServiceImpl.mapToOrderDTO(order);
            orderDTOs.add(orderDTO);
        }
        return new DataResponse<>(orderDTOs);
    }
}
