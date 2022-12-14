package com.hung.sneakery.utils.config;

import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;

@Configuration
public class RedisConfig {
    @Bean
    public LettuceClientConfigurationBuilderCustomizer lettuceClientConfigurationBuilderCustomizer() {
        return clientConfigurationBuilder -> {
            if (clientConfigurationBuilder.build().isUseSsl()) {
                clientConfigurationBuilder.useSsl().disablePeerVerification();
            }
        };
    }
    @Bean
    public RedisConfiguration defaultRedisConfig() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName("ec2-50-16-82-63.compute-1.amazonaws.com");
        config.setPassword(RedisPassword.of("p1ac07341d814e8e016e5e9b168c8ad5f84495cbe76e4810aa8f1d49e758a9d06"));
        return config;
    }
}
