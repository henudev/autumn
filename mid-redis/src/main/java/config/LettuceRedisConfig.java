package config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value="spring.redis.lettuce.enable",havingValue="true", matchIfMissing=true)
public class LettuceRedisConfig {
    
}
