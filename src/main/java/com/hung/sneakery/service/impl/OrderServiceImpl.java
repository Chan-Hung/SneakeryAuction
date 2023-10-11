package com.hung.sneakery.service.impl;

import com.hung.sneakery.converter.OrderConverter;
import com.hung.sneakery.dto.OrderDTO;
import com.hung.sneakery.dto.response.DataResponse;
import com.hung.sneakery.entity.Order;
import com.hung.sneakery.entity.User;
import com.hung.sneakery.repository.OrderRepository;
import com.hung.sneakery.repository.UserRepository;
import com.hung.sneakery.service.OrderService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private OrderConverter orderConverter;

    public DataResponse<List<OrderDTO>> getAllByUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User winner = userRepository.findByUsername(userName);
        List<Order> orders = orderRepository.findByWinner(winner);
        return new DataResponse<>(orderConverter.convertToOrderDTOList(orders));
    }
}
