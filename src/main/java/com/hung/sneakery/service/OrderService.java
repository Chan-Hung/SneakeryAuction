package com.hung.sneakery.service;

import com.hung.sneakery.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    /**
     * Get All Orders By User
     *
     * @return List<OrderDTO>
     */
    List<OrderDTO> getAllByUser();
}
