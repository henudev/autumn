package config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value="spring.redis.jedis.enable",havingValue="true", matchIfMissing=false)
public class JedisRedisConfig {

}
