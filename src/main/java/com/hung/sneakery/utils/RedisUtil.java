package com.hung.sneakery.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.function.Function;

@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public <T> T getOrLoadFromCache(String key, Class<T> type, Function<Void, T> loader) {
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();

        // Try to fetch the value from Redis
        T value = (T) opsForValue.get(key);

        if (value == null) {
            // If not found in Redis, load from the loader function
            value = loader.apply(null);

            // Store the value in Redis
            opsForValue.set(key, value);
        }

        return value;
    }
}
