package com.hung.sneakery.converter.impl;

import com.hung.sneakery.converter.OrderConverter;
import com.hung.sneakery.converter.ProductConverter;
import com.hung.sneakery.dto.OrderDTO;
import com.hung.sneakery.entity.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrderConverterImpl implements OrderConverter {

    @Resource
    private ProductConverter productConverter;

    @Override
    public OrderDTO convertToOrderDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .product(productConverter.convertToProductDTO(order.getBid().getProduct()))
                .status(order.getStatus())
                .priceWin(order.getBid().getPriceWin())
                .build();
    }

    @Override
    public List<OrderDTO> convertToOrderDTOList(List<Order> orders) {
        return Optional.ofNullable(orders).orElse(Collections.emptyList())
                .stream().map(this::convertToOrderDTO).collect(Collectors.toList());
    }
}
