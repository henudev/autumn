package redis.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@ConditionalOnProperty(value="spring.redis.jedis.enable",havingValue="true", matchIfMissing=false)
@Slf4j
public class JedisRedisConfig {
    @PostConstruct
    public void test(){
        log.warn("jedis DONE!!!!");
    }
}
