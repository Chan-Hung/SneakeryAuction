package com.hung.sneakery.data.remotes.services.impl;

import com.hung.sneakery.data.models.dto.OrderDTO;
import com.hung.sneakery.data.models.dto.ProductDTO;
import com.hung.sneakery.data.models.dto.response.DataResponse;
import com.hung.sneakery.data.models.entities.Order;
import com.hung.sneakery.data.models.entities.User;
import com.hung.sneakery.data.remotes.repositories.OrderRepository;
import com.hung.sneakery.data.remotes.repositories.UserRepository;
import com.hung.sneakery.data.remotes.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    OrderRepository orderRepository;

    @Resource
    UserRepository userRepository;

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
