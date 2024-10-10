package com.example.Login.configs;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitConfig {

    private static final String RATE_LIMIT_KEY_PREFIX = "rate_limit:";
    private static final int MAX_REQUESTS = 5; // Maximum requests
    private static final long TIME_WINDOW = 60; // Time window in seconds

    private final RedisTemplate<String, Integer> redisTemplate;

    @Before("execution(* com.example.Login.*(..))")
    public void limitRate(JoinPoint joinPoint) {
        String key = RATE_LIMIT_KEY_PREFIX + joinPoint.getSignature().getName();

        // Increment the request count
        Integer currentCount = redisTemplate.opsForValue().increment(key, 1);

        // Set expiration if this is the first request
        if (currentCount == 1) {
            redisTemplate.expire(key, TIME_WINDOW, TimeUnit.SECONDS);
        }

        // Check if the limit has been exceeded
        if (currentCount != null && currentCount > MAX_REQUESTS) {
            throw new RuntimeException("Rate limit exceeded. Try again later.");
        }
    }
}
