package com.marcusnogueiraa.urlshortener.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String ACCESS_COUNT_KEY_PREFIX = "url:accessCount:";

    public Long getUrlAccessCount(String shortUrlCode){
        String accessCountKey = ACCESS_COUNT_KEY_PREFIX + shortUrlCode;
        Object result = redisTemplate.opsForValue().get(accessCountKey);
        if (result == null) return 0L;
        return Long.parseLong(result.toString());
    }

    public void incrementUrlAccessCount(String shortUrlCode) {
        String accessCountKey = ACCESS_COUNT_KEY_PREFIX + shortUrlCode;
        redisTemplate.opsForValue().increment(accessCountKey);
    }

    public void clearUrlAccessCount(String shortUrlCode) {
        String accessCountKey = ACCESS_COUNT_KEY_PREFIX + shortUrlCode;
        redisTemplate.delete(accessCountKey);
    }
}
