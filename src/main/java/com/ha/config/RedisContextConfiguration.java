package com.ha.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableTransactionManagement
public class RedisContextConfiguration {
	
	@Autowired
	private AppConfig config;
	
//	@Autowired
//	private RedisTemplate<Object, Object> template;
//	
//	@Constru
//	public void init() {
//		template.setEnableTransactionSupport(true);
//		template.setKeySerializer(new StringRedisSerializer());
////		redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
//	}

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
		return redisTemplate;
	}
}
