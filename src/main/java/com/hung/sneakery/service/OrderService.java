package com.hung.sneakery.service;

import com.hung.sneakery.dto.OrderDTO;
import com.hung.sneakery.dto.request.OrderRequest;
import com.hung.sneakery.enums.EOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    /**
     * Get all Orders
     *
     * @param pageable Pageable
     * @return Page<OrderDTO>
     */
    Page<OrderDTO> getAll(Pageable pageable);

    /**
     * Get Orders By User (Winner)
     *
     * @param id          Long
     * @param pageable    Pageable
     * @param orderStatus EOrderStatus
     * @return Page<OrderDTO>
     */
    Page<OrderDTO> getByUser(Long id, Pageable pageable, EOrderStatus orderStatus);

    /**
     * Update Order
     *
     * @return OrderDTO
     */
    OrderDTO update(Long id, OrderRequest request);

    /**
     * Delete order
     *
     * @param id Long
     * @return OrderDTO
     */
    OrderDTO delete(Long id);
}
