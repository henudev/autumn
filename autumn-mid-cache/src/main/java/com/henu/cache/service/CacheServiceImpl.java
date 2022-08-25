package com.henu.cache.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CacheServiceImpl implements CacheService{

    @Autowired
    private ValueOperations<String, Object> valueOperation;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public Object get(String key) {
        return valueOperation.get(key);
    }

    @Override
    public void set(String key, Object value) {
        valueOperation.set(key, value);
    }

    @Override
    public void set(String key, Object value, long timeout) {
        valueOperation.set(key,value,timeout, TimeUnit.SECONDS);
    }
}