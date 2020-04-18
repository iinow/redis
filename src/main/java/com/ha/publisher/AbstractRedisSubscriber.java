package com.ha.publisher;

import com.ha.common.type.RedisTopicType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

@Slf4j
public abstract class AbstractRedisSubscriber implements MessageListener {

    private final RedisTopicType topicType;

    public AbstractRedisSubscriber(RedisTopicType topicType) {
        this.topicType = topicType;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            RedisTopicType type = RedisTopicType.valueOf(new String(pattern));
            log.info("message arrived: {}", type);
            if(!topicType.equals(type)) {
                return;
            }
            onMessage(message);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public abstract void onMessage(Message message);
}
