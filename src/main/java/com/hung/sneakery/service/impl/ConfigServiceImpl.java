package com.hung.sneakery.service.impl;

import com.hung.sneakery.config.AppProperties;
import com.hung.sneakery.dto.ConfigDTO;
import com.hung.sneakery.dto.request.ConfigRequest;
import com.hung.sneakery.service.ConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@RequiredArgsConstructor
public class ConfigServiceImpl implements ConfigService {

    @Resource
    private final AppProperties appProperties;

    @Override
    public ConfigDTO update(ConfigRequest configDTO) {
        appProperties.setExtendedMinute(configDTO.getExtendedMinute());
        return ConfigDTO.builder().extendedMinute(appProperties.getExtendedMinute()).build();
    }

    @Override
    public ConfigDTO getAll() {
        return ConfigDTO.builder().extendedMinute(appProperties.getExtendedMinute()).build();
    }
}