package com.ha.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app.redis")
public class AppRedisProperties {
	private String host;
	private int port;
	private int database;
}
