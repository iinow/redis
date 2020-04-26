package com.ha.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import redis.embedded.RedisServer;

import javax.annotation.PreDestroy;
import java.io.IOException;

@Slf4j
@Configuration
public class EmbeddedRedisConfig {

    private final AppRedisProperties redisProperties;

    private RedisServer redisServer;

    public EmbeddedRedisConfig(AppRedisProperties redisProperties) throws IOException {
        this.redisProperties = redisProperties;
    }

    @EventListener(ApplicationStartedEvent.class)
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
