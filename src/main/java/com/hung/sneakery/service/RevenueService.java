package com.hung.sneakery.service;

import com.hung.sneakery.data.models.dto.request.GetRevenue;
import com.hung.sneakery.data.models.dto.response.DataResponse;

public interface RevenueService {

    DataResponse<GetRevenue> getRevenue();
}
