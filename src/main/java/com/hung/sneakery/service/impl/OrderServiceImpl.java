package com.hung.sneakery.service.impl;

import com.hung.sneakery.converter.OrderConverter;
import com.hung.sneakery.dto.OrderDTO;
import com.hung.sneakery.dto.request.OrderRequest;
import com.hung.sneakery.entity.Order;
import com.hung.sneakery.entity.User;
import com.hung.sneakery.exception.NotFoundException;
import com.hung.sneakery.repository.OrderRepository;
import com.hung.sneakery.repository.UserRepository;
import com.hung.sneakery.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<OrderDTO> getAll(final Pageable pageable) {
        Page<Order> ordersPage = orderRepository.findAll(pageable);
        List<OrderDTO> orderDTOs = orderConverter.convertToOrderDTOList(ordersPage.getContent());
        return new PageImpl<>(orderDTOs, pageable, ordersPage.getTotalElements());
    }

    @Override
    public Page<OrderDTO> getByUser(final Long id, final Pageable pageable) {
        User winner = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        Page<Order> ordersPage = orderRepository.findByWinner(winner, pageable);
        List<OrderDTO> orderDTOs = orderConverter.convertToOrderDTOList(ordersPage.getContent());
        return new PageImpl<>(orderDTOs, pageable, ordersPage.getTotalElements());
    }

    @Override
    public OrderDTO update(final Long id, final OrderRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found"));
        order.setStatus(request.getStatus());
        return orderConverter.convertToOrderDTO(orderRepository.save(order));
    }

    @Override
    public OrderDTO delete(final Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found"));
        orderRepository.delete(order);
        return orderConverter.convertToOrderDTO(order);
    }
}