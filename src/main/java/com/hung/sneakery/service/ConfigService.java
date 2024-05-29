package com.hung.sneakery.service;

import com.hung.sneakery.dto.request.ConfigRequest;
import com.hung.sneakery.dto.response.BaseResponse;

public interface ConfigService {

    BaseResponse update(ConfigRequest request);
}
