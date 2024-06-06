package com.hung.sneakery.controller.admin;

import com.hung.sneakery.dto.ConfigDTO;
import com.hung.sneakery.dto.request.ConfigRequest;
import com.hung.sneakery.service.ConfigService;
import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "Config APIs")
@RequestMapping("/configs")
@PreAuthorize("hasRole('ADMIN')")
public class ConfigController {

    @Resource
    private ConfigService configService;

    @PutMapping
    public ConfigDTO update(@RequestBody final ConfigRequest request) {
        return configService.update(request);
    }

    @GetMapping
    public ConfigDTO getAll() {
        return configService.getAll();
    }
}
