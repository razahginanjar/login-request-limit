package com.example.Login.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RateLimiterConfig {
    private final StringRedisTemplate redisTemplate;

    private static final String RATE_LIMIT_KEY_PREFIX = "rate_limit:";
    private static final int MAX_REQUESTS = 5; // Max requests per second

    public boolean isAllowed(String clientId) {
        String key = RATE_LIMIT_KEY_PREFIX + clientId;
        Long requestCount = redisTemplate.opsForValue().increment(key);

        if (requestCount == 1) {
            redisTemplate.expire(key, 1, TimeUnit.SECONDS);
        }

        return requestCount <= MAX_REQUESTS;
    }
}
