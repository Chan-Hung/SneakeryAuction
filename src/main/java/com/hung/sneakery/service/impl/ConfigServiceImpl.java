package com.hung.sneakery.service.impl;

import com.hung.sneakery.dto.request.ConfigRequest;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.service.ConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfigServiceImpl implements ConfigService {

    private final ConfigurableEnvironment environment;

    @Value("${hung.com.extendedMinute}")
    private Integer extendedMinute;

    @Override
    public BaseResponse update(ConfigRequest configRequest) {
        log.info(extendedMinute.toString());
        environment.getPropertySources().addFirst(new MapPropertySource("dynamic",
                Collections.singletonMap(configRequest.getKey(), configRequest.getValue())));
        log.info(extendedMinute.toString());
        return BaseResponse.builder().success(true).message("Update config successfully").build();
    }
}