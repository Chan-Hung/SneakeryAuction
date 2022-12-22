package com.hung.sneakery.data.remotes.services;

import com.hung.sneakery.data.models.dto.OrderDTO;
import com.hung.sneakery.data.models.dto.request.GetRevenue;
import com.hung.sneakery.data.models.dto.response.DataResponse;

import java.util.List;

public interface RevenueService {
    DataResponse<List<OrderDTO>> getAllByAdmin();

    DataResponse<GetRevenue> getRevenue();
}
