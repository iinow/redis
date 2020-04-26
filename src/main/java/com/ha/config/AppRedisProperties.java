package com.ha.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app.redis")
public class AppRedisProperties {
    private String host;
    private int port;
    private int database;
    private String password;
    private Cluster cluster = new Cluster();

    @Data
    public static class Cluster {
    	private String password;
        private List<String> nodes = Collections.emptyList();
    }
}
