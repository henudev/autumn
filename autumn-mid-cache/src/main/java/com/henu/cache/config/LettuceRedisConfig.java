package com.henu.cache.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.*;

import java.io.Serializable;

@Configuration
@Slf4j
public class LettuceRedisConfig implements RedisSerializer<Object> {

    private final Converter<Object, byte[]> serializer = new SerializingConverter();
    private final Converter<byte[], Object> deserializer = new DeserializingConverter();

    static final byte[] EMPTY_ARRAY = new byte[0];

    private final RedisConnectionFactory lettuceConnectionFactory;

    @Autowired
    public LettuceRedisConfig (RedisConnectionFactory redisConnectionFactory){
        this.lettuceConnectionFactory = redisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        initDomainRedisTemplate(redisTemplate, lettuceConnectionFactory);
        log.debug("-------------RedisTemplate init Done-------------");
        return redisTemplate;
    }

    /**
     * 设置数据存入 redis 的序列化方式
     * @param redisTemplate redisTemplate
     * @param factory 连接池
     */
    private void initDomainRedisTemplate(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory factory) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setConnectionFactory(factory);
    }

    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }
    @Bean
    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }
    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }
    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }
    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }

    @Override
    public byte[] serialize(Object object) throws SerializationException {
        if(object == null){
            return EMPTY_ARRAY;
        }
        try {
            return serializer.convert(object);
        } catch (Exception ex) {
            return EMPTY_ARRAY;
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            return deserializer.convert(bytes);
        } catch (Exception ex) {
            throw new SerializationException("Cannot deserialize", ex);
        }
    }
}
