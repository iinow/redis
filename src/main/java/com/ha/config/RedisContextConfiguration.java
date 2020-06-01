package com.ha.config;

import com.ha.common.type.RedisTopicType;
import com.ha.subscriber.RedisSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
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

    @Autowired
    private RedisSubscriber subscriber;

    private final AppRedisProperties redisProperties;

    private final RedisProperties redisYaml;

    public RedisContextConfiguration(AppRedisProperties redisProperties, RedisProperties redisYaml) {
        this.redisProperties = redisProperties;
        this.redisYaml = redisYaml;
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration standConfig = new RedisStandaloneConfiguration();
        standConfig.setHostName(redisYaml.getHost());
        standConfig.setPort(redisYaml.getPort());
        standConfig.setDatabase(redisYaml.getDatabase());
        return new LettuceConnectionFactory(standConfig);
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return lettuceConnectionFactory();
    }

    @Bean
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        return lettuceConnectionFactory();
    }

    /*@Bean
    public RedisConnectionFactory redisClusterConnectionFactory() {
        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(redisProperties.getCluster().getNodes());
        clusterConfiguration.setPassword(redisProperties.getCluster().getPassword());
        return new LettuceConnectionFactory(clusterConfiguration);
    }*/

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
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

    /*@Bean
    public RedisTemplate<String, String> clusterRedisTemplate() {
      RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
      redisTemplate.setConnectionFactory(redisClusterConnectionFactory());
      redisTemplate.setKeySerializer(RedisSerializer.string());
      redisTemplate.setValueSerializer(RedisSerializer.string());
      redisTemplate.setHashKeySerializer(RedisSerializer.string());
      redisTemplate.setHashValueSerializer(RedisSerializer.string());
      return redisTemplate;
    }*/

    @Bean
    public ReactiveRedisTemplate<String, String> reactiveRedisTemplate(ReactiveRedisConnectionFactory reactiveRedisConnectionFactory,
                                                                       RedisSerializationContext<String, String> redisSerializationContext) {
        return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory, redisSerializationContext);
    }

    @Bean
    public RedisSerializationContext<String, String> redisSerializationContext() {
        return RedisSerializationContext
                .<String, String>newSerializationContext()
                .key(RedisSerializer.string())
                .value(RedisSerializer.string())
                .hashKey(RedisSerializer.string())
                .hashValue(RedisSerializer.string())
                .build();
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put("USER", userRedisCacheConfiguration());

        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(defaultRedisCacheConfiguration())
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }

    @Bean
    public RedisCacheConfiguration userRedisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(Duration.ofSeconds(100))
//                .computePrefixWith(CacheKeyPrefix.simple())
                .computePrefixWith(name -> name + ":")
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer(StandardCharsets.UTF_8)));
    }

    @Bean
    public RedisCacheConfiguration defaultRedisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(Duration.ofSeconds(5))
//                .computePrefixWith(CacheKeyPrefix.simple())
                .computePrefixWith(name -> name + ":")
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        return container;
    }

    @PostConstruct
    public void initRedisMessageListener() {
        RedisMessageListenerContainer listenerContainer = redisMessageListenerContainer(redisConnectionFactory());
        listenerContainer.addMessageListener(subscriber, RedisTopicType.findChannel(RedisTopicType.T1));
    }
}
