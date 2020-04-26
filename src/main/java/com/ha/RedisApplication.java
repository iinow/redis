package com.ha;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@SpringBootApplication
public class RedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}

	@Autowired
	private RedisTemplate<String, String> clusterRedisTemplate;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@EventListener(ApplicationReadyEvent.class)
	public void init() {
		clusterRedisTemplate.opsForValue().set("test", "dkdkd");
		String value = clusterRedisTemplate.opsForValue().get("test");

//		redisTemplate.opsForValue().set("haha", "hahaha2");
		Object value2 = redisTemplate.opsForValue().get("test");

		log.info(value + ", " + value2);
	}
}
