package com.ha.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Slf4j
@Configuration
public class EmbeddedRedisConfig {

    private final AppRedisProperties redisProperties;

    private RedisServer redisServer;

    public EmbeddedRedisConfig(AppRedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    @PostConstruct
    public void redisServer() throws IOException {
        redisServer = new RedisServer(redisProperties.getPort());
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }
}
