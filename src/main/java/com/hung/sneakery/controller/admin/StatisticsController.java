package com.hung.sneakery.controller.admin;

import com.hung.sneakery.dto.response.StatisticsResponse;
import com.hung.sneakery.service.StatisticsService;
import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = "Statistics APIs")
@RequestMapping("/statistics")
public class StatisticsController {

    @Resource
    private StatisticsService statisticsService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public StatisticsResponse getStatistics() {
        return statisticsService.getStatisticsResult();
    }
}
