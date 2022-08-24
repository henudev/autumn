package com.henu.cache.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 常用方法.
 */
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;

    public RedisUtil(){

    }

    /**
     * remove keys
     * @param keys 键
     */
    public void remove(String... keys) {
        String[] var2 = keys;
        int var3 = keys.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String key = var2[var4];
            this.remove(key);
        }
    }

    /**
     * 根据模式remove keys
     * @param pattern 自定义模式
     */
    public void removePattern(String pattern) {
        Set keys = this.redisTemplate.keys(pattern);
        if(keys.size() > 0) {
            this.redisTemplate.delete(keys);
        }
    }

    /**
     * remove一个key
     * @param key 键
     */
    public void remove(String key) {
        if(this.exists(key)) {
            this.redisTemplate.delete(key);
        }

    }

    /**
     * 判断是否存在
     * @param key 键
     * @return BOOLEAN
     */
    public boolean exists(String key) {
        return this.redisTemplate.hasKey(key).booleanValue();
    }

    /**
     * GET VALUE
     * @param key 键
     * @return
     */
    public Object get(String key) {
        Object result = null;
        ValueOperations operations = this.redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * set key value
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean set(String key, Object value) {
        boolean result = false;

        try {
            ValueOperations e = this.redisTemplate.opsForValue();
            e.set(key, value);
            result = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    /**
     * 设置一个含有过期时间的key value
     * @param key 键
     * @param value 值
     * @param expireTime 过期时间 秒
     * @return BOOLEAN
     */
    public boolean set(String key, Object value, Long expireTime) {
        boolean result = false;

        try {
            ValueOperations e = this.redisTemplate.opsForValue();
            e.set(key, value);
            this.redisTemplate.expire(key, expireTime.longValue(), TimeUnit.SECONDS);
            result = true;
        } catch (Exception var6) {
            throw new RuntimeException(var6);
        }
        return result;
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setIfAbsent(String key, Object value) {
        boolean result = false;

        try {
            ValueOperations e = this.redisTemplate.opsForValue();
            result = e.setIfAbsent(key, value).booleanValue();
        } catch (Exception var5) {
            throw new RuntimeException(var5);
        }

        return result;
    }
}
