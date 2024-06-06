package com.hung.sneakery.config;

import com.hung.sneakery.utils.SneakeryConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = SneakeryConstant.HUNG_PREFIX)
@Data
public class AppProperties {
    private Integer extendedMinute;
}