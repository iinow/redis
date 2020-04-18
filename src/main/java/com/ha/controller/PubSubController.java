package com.ha.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ha.common.type.RedisTopicType;
import com.ha.message.TestMessage;
import com.ha.publisher.RedisTestPublisher;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/pubsub")
@RestController
public class PubSubController {

    private final RedisTestPublisher redisTestPublisher;

    public PubSubController(RedisTestPublisher redisTestPublisher) {
        this.redisTestPublisher = redisTestPublisher;
    }

    @PostMapping
    public void sendMessage(
            @RequestParam(name = "topicType") RedisTopicType topicType,
            @RequestParam(name = "content") String content) throws JsonProcessingException {
        redisTestPublisher.publish(topicType, new TestMessage(content));
    }
}
