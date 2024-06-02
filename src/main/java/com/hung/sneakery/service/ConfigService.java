package com.hung.sneakery.service;

import com.hung.sneakery.dto.ConfigDTO;

public interface ConfigService {

    ConfigDTO update(ConfigDTO request);

    ConfigDTO getAll();
}
