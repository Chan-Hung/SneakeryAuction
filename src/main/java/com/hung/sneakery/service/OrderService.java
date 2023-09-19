package com.hung.sneakery.service;

import com.hung.sneakery.dto.OrderDTO;
import com.hung.sneakery.dto.response.DataResponse;

import java.util.List;

public interface OrderService {

    DataResponse<List<OrderDTO>> getAllByUser();
}
