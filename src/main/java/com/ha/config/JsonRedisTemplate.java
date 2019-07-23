package com.ha.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonRedisTemplate<V> /* extends RedisTemplate<String, V> */{

	/*
	 * public JsonRedisTemplate(RedisConnectionFactory connectionFactory,
	 * ObjectMapper objectMapper, Class valueType) { RedisSerializer<String>
	 * stringSerializer = new StringRedisSerializer();
	 * super.setKeySerializer(stringSerializer);
	 * super.setHashKeySerializer(stringSerializer);
	 * super.setHashValueSerializer(stringSerializer); Jackson2JsonRedisSerializer
	 * jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(valueType);
	 * jsonRedisSerializer.setObjectMapper(objectMapper);
	 * super.setValueSerializer(jsonRedisSerializer);
	 * super.setConnectionFactory(connectionFactory); super.afterPropertiesSet(); }
	 */

//	@Bean
//	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
//		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//		container.setConnectionFactory(connectionFactory);
//		container.addMessageListener(listenerAdapter, new PatternTopic("sendMessage"));
//		container.addMessageListener(topicMessageListener(), new PatternTopic("sendMessage"));
//		return container;
//	}

	/*
	 * @Bean public TopicMessageListener topicMessageListener() { return new
	 * TopicMessageListener(); }
	 * 
	 * @Bean MessageListenerAdapter listenerAdapter(MethodMessageListener
	 * methodMessageListener) { return new
	 * MessageListenerAdapter(methodMessageListener, "sendMessage"); }
	 * 
	 * @Bean MethodMessageListener methodMessageListener() { return new
	 * MethodMessageListener(); }
	 */

	/*
	 * @Autowired private JsonRedisTemplate jsonRedisTemplate;
	 * 
	 * @Bean public ObjectMapper objectMapper() { return new ObjectMapper(); }
	 * 
	 * @Bean public JsonRedisTemplate jsonRedisTemplate(RedisConnectionFactory
	 * connectionFactory, ObjectMapper objectMapper) { return new
	 * JsonRedisTemplate<>(connectionFactory, objectMapper, Object.class); }
	 */
}
