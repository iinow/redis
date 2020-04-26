package com.ha.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ha.common.type.RedisTopicType;
import com.ha.subscriber.RedisSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
@EnableTransactionManagement
public class RedisContextConfiguration {

    private final AppRedisProperties redisProperties;

    public RedisContextConfiguration(AppRedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration standConfig = new RedisStandaloneConfiguration();
        standConfig.setHostName(redisProperties.getHost());
        standConfig.setPort(redisProperties.getPort());
        standConfig.setDatabase(redisProperties.getDatabase());
        return new LettuceConnectionFactory(standConfig);
    }

    @Bean
    public RedisConnectionFactory redisClusterConnectionFactory() {
        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(redisProperties.getCluster().getNodes());
        clusterConfiguration.setPassword(redisProperties.getCluster().getPassword());
        return new LettuceConnectionFactory(clusterConfiguration);
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
        redisTemplate.setKeySerializer(RedisSerializer.string());
//		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setValueSerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(RedisSerializer.string());
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, String> clusterRedisTemplate() {
      RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
      redisTemplate.setConnectionFactory(redisClusterConnectionFactory());
      redisTemplate.setKeySerializer(RedisSerializer.string());
      redisTemplate.setValueSerializer(RedisSerializer.string());
      redisTemplate.setHashKeySerializer(RedisSerializer.string());
      redisTemplate.setHashValueSerializer(RedisSerializer.string());
      return redisTemplate;
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(Duration.ofSeconds(180))
                .computePrefixWith(CacheKeyPrefix.simple())
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        // User
        cacheConfigurations.put("USER", RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(5))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer(StandardCharsets.UTF_8))));
        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory).cacheDefaults(configuration)
                .withInitialCacheConfigurations(cacheConfigurations).build();
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        return container;
    }

    @Autowired
    private RedisSubscriber subscriber;

    @PostConstruct
    public void initRedisMessageListener() {
        RedisTopicType.topicMap.values().forEach(channelTopic -> redisMessageListenerContainer(redisConnectionFactory()).addMessageListener(subscriber, channelTopic));
    }
}
