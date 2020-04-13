package com.ha.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class Value {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void operateValue() {
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        valueOps.set("key1", "key1value");
        valueOps.set("key2", "key2value");

        assertThat(redisTemplate.hasKey("key1")).isTrue();
        assertThat(2L).isEqualTo(redisTemplate.countExistingKeys(Arrays.asList("key1", "key2", "key3")));
        assertThat(redisTemplate.expireAt("key1", Date.from(LocalDateTime.now().plusDays(1L).atZone(ZoneId.systemDefault()).toInstant()))).isTrue();
        assertThat(redisTemplate.expire("key2", 1000L, TimeUnit.SECONDS)).isTrue();
        assertThat(redisTemplate.getExpire("key1", TimeUnit.SECONDS)).isNotNull();
        assertThat(redisTemplate.persist("key1")).isTrue();
        assertThat(redisTemplate.getExpire("key1")).isEqualTo(-1L);
        assertThat(redisTemplate.delete("key1")).isTrue();
    }
}
