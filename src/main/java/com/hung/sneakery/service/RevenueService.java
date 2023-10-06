package com.hung.sneakery.service;

import com.hung.sneakery.dto.request.GetRevenue;
import com.hung.sneakery.dto.response.DataResponse;

public interface RevenueService {

    DataResponse<GetRevenue> getRevenue();
}
