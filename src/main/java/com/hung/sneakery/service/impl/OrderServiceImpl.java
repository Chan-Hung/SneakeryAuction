package com.hung.sneakery.service.impl;

import com.hung.sneakery.dto.OrderDTO;
import com.hung.sneakery.dto.ProductDTO;
import com.hung.sneakery.dto.response.DataResponse;
import com.hung.sneakery.entities.Order;
import com.hung.sneakery.entities.User;
import com.hung.sneakery.repository.OrderRepository;
import com.hung.sneakery.repository.UserRepository;
import com.hung.sneakery.service.OrderService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private UserRepository userRepository;

    public DataResponse<List<OrderDTO>> getAllByUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User winner = userRepository.findByUsername(userName);

        List<Order> orders = orderRepository.findByWinner(winner);

        List<OrderDTO> orderDTOs = new ArrayList<>();
        for(Order order: orders){
            OrderDTO orderDTO = mapToOrderDTO(order);
            orderDTOs.add(orderDTO);
        }
        return new DataResponse<>(orderDTOs);
    }

    public static OrderDTO mapToOrderDTO(Order order){
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setId(order.getId());
        ProductDTO productDTO = new ProductDTO(order.getBid().getProduct());
        orderDTO.setProduct(productDTO);
        orderDTO.setPriceWin(order.getBid().getPriceWin());

        return orderDTO;
    }
}
