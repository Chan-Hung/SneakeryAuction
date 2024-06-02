package com.hung.sneakery.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hung")
@Data
public class AppProperties {
    private Integer extendedMinute;
}