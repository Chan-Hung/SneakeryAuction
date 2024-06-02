package com.hung.sneakery.service;

import com.hung.sneakery.dto.ConfigDTO;
import com.hung.sneakery.dto.request.ConfigRequest;

public interface ConfigService {

    ConfigDTO update(ConfigRequest request);

    ConfigDTO getAll();
}
