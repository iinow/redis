package com.ha;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import javax.annotation.PostConstruct;

@Slf4j
@SpringBootApplication
public class RedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@Autowired
	private ReactiveRedisConnectionFactory reactiveRedisConnectionFactory;

	@PostConstruct
	public void init() {
		log.info(redisConnectionFactory.toString());
		log.info(reactiveRedisConnectionFactory.toString());
	}
}
