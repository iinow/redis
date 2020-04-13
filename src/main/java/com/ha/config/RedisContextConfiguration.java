package com.ha.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class RedisContextConfiguration {
	
	private final AppConfig config;

	public RedisContextConfiguration(AppConfig config) {
		this.config = config;
	}

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration standConfig = new RedisStandaloneConfiguration();
		standConfig.setHostName(config.getRedis().getHost());
		standConfig.setPort(config.getRedis().getPort());
		standConfig.setDatabase(config.getRedis().getDatabase());
		return new LettuceConnectionFactory(standConfig);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		ObjectMapper objectMapper = new ObjectMapper();
//	    objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
//	    objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
//	    objectMapper.registerModule(new JavaTimeModule());
	    
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setEnableTransactionSupport(true);
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
//		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		LettuceConnectionFactory factory = new LettuceConnectionFactory();
		return redisTemplate;
	}
}
