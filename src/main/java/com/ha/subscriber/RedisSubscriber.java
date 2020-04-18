package com.ha.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ha.common.type.RedisTopicType;
import com.ha.message.TestMessage;
import com.ha.publisher.AbstractRedisSubscriber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisSubscriber extends AbstractRedisSubscriber {

    private ObjectMapper objectMapper = new ObjectMapper();

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisSubscriber(RedisTemplate<String, Object> redisTemplate) {
        super(RedisTopicType.T1);
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onMessage(Message message) {
        try {
            String body = redisTemplate.getStringSerializer().deserialize(message.getBody());
            TestMessage testMessage = objectMapper.readValue(body, TestMessage.class);
            log.info("body: {}", testMessage.toString());
        } catch (Exception e) {
            log.error("error ", e);
        }
    }
}
