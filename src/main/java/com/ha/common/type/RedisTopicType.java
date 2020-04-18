package com.ha.common.type;

import org.springframework.data.redis.listener.ChannelTopic;

import java.util.HashMap;
import java.util.Map;

public enum RedisTopicType {
    T1,
    T2;

    public static Map<RedisTopicType, ChannelTopic> topicMap = new HashMap<>();

    static {
        for(RedisTopicType topicType: RedisTopicType.values()) {
            topicMap.put(topicType, new ChannelTopic(topicType.name()));
        }
    }

    public static ChannelTopic findChannel(RedisTopicType type) {
        return RedisTopicType.topicMap.get(type);
    }
}
