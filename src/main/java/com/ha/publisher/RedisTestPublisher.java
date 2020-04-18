package com.ha.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ha.common.type.RedisTopicType;
import com.ha.message.TestMessage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisTestPublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    public RedisTestPublisher(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void publish(RedisTopicType topicType, TestMessage testMessage) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(testMessage);
        redisTemplate.convertAndSend(RedisTopicType.findChannel(topicType).getTopic(), json);
    }
}
