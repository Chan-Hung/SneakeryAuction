package com.hung.sneakery.controller.admin;

import com.hung.sneakery.dto.ConfigDTO;
import com.hung.sneakery.service.ConfigService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "Config APIs")
@RequestMapping("/configs")
public class ConfigController {

    @Resource
    private ConfigService configService;

    @PutMapping
    public ConfigDTO update(@RequestBody final ConfigDTO request) {
        return configService.update(request);
    }

    @GetMapping
    public ConfigDTO getAll() {
        return configService.getAll();
    }
}
