package com.ha.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ha.entity.Address;

@Configuration
@EnableTransactionManagement
public class RedisContextConfiguration {
	
	@Value(value = "${spring.redis.host}")
	private	String host;
	
	@Value(value = "${spring.redis.port}")
	private	int port;
	
	@Value(value = "${spring.redis.database}")
	private	int index;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration standConfig = new RedisStandaloneConfiguration();
		standConfig.setHostName(host);
		standConfig.setPort(port);
		standConfig.setDatabase(index);
		return new LettuceConnectionFactory(standConfig);
	}

	@Bean
	public RedisTemplate<String, Address> redisTemplate() {
		RedisTemplate<String, Address> redisTemplate = new RedisTemplate<>();
		redisTemplate.setEnableTransactionSupport(true);
		redisTemplate.setConnectionFactory(redisConnectionFactory());
//		redisTemplate.setKeySerializer(new StringRedisSerializer());
//		redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
		return redisTemplate;
	}
}
