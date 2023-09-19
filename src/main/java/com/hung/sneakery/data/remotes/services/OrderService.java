package com.hung.sneakery.data.remotes.services;

import com.hung.sneakery.data.models.dto.OrderDTO;
import com.hung.sneakery.data.models.dto.response.DataResponse;

import java.util.List;

public interface OrderService {

    DataResponse<List<OrderDTO>> getAllByUser();
}
