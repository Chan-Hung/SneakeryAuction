package com.hung.sneakery.controller.admin;

import com.hung.sneakery.dto.request.ConfigRequest;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.service.ConfigService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@Api(tags = "Config APIs")
@RequestMapping("/configs")
public class ConfigController {

    @Resource
    private ConfigService configService;

    @PutMapping
    public BaseResponse update(@Valid @RequestBody final ConfigRequest request) {
        return configService.update(request);
    }
}
