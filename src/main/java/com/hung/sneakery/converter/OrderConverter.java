package com.hung.sneakery.converter;

import com.hung.sneakery.dto.OrderDTO;
import com.hung.sneakery.entity.Order;

import java.util.List;

public interface OrderConverter {

    OrderDTO convertToOrderDTO(Order order);

    List<OrderDTO> convertToOrderDTOList(List<Order> orders);
}
