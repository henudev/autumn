package com.henu.cache.service;

/**
 * CacheService.
 */
public interface CacheService {

    boolean hasKey(String key);

    boolean delete(String key);

    Object get(String key);

    void set(String key, Object value);

    void set(String key, Object value, long timeout);
}
