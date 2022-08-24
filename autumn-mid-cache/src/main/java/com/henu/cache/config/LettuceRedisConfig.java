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
import org.springframework.data.redis.core.RedisTemplate;
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
    public RedisTemplate<String, Serializable> redisTemplate(){
        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
        initDomainRedisTemplate(redisTemplate, lettuceConnectionFactory);
        log.debug("-------------RedisTemplate init Done-------------");
        return redisTemplate;
    }

    /**
     * 设置数据存入 redis 的序列化方式
     * @param redisTemplate redisTemplate
     * @param factory 连接池
     */
    private void initDomainRedisTemplate(RedisTemplate<String, Serializable> redisTemplate, RedisConnectionFactory factory) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setConnectionFactory(factory);
    }


    @Override
    public byte[] serialize(Object o) throws SerializationException {
        return new byte[0];
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        return null;
    }

    @Override
    public boolean canSerialize(Class<?> type) {
        return RedisSerializer.super.canSerialize(type);
    }

    @Override
    public Class<?> getTargetType() {
        return RedisSerializer.super.getTargetType();
    }


}
