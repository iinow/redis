package com.ha.redis;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
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
public class ValueOperationTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * key:value = 1:1
     * */
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

    /**
     * key:value = 1:N
     * 순서 O, 중복 O
     * */
    @Test
    public void operateList() {
        final String key = "key";
        ListOperations<String, Object> listOps = redisTemplate.opsForList();
        listOps.leftPush(key, "leftValue1");
        listOps.rightPush(key, "rightValue1");

        assertThat(listOps.size(key)).isEqualTo(2L);
        assertThat(listOps.leftPop(key)).isEqualTo("leftValue1");
        assertThat(listOps.rightPop(key)).isEqualTo("rightValue1");
        assertThat(listOps.size(key)).isEqualTo(0L);
    }

    /**
     * 순서 X, key 중복 X, value 중복 O
     * */
    @Test
    public void operateHash() {
        final String key = "key";
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        hashOps.put(key, "hashKey1", "1");
        hashOps.put(key, "hashKey2", "2");

        assertThat(redisTemplate.type(key)).isEqualTo(DataType.HASH);
        assertThat(hashOps.size(key)).isEqualTo(2L);
        assertThat(hashOps.increment(key, "hashKey1", 2)).isEqualTo(3L);
        assertThat(hashOps.delete(key, "hashKey1")).isEqualTo(1L);
        assertThat(hashOps.get(key, "hashKey1")).isNull();
    }

    /**
     * 순서 X, value 중복 X
     * */
    @Test
    public void operateSet() {
        final String key = "key";
        final String otherKey = "otherKey";
        final String destKey = "destKey";

        SetOperations<String, Object> setOps = redisTemplate.opsForSet();
        setOps.add(key, "1", "2", "3");
        setOps.add(otherKey, "3", "4", "5");

        assertThat(setOps.isMember(key, "1")).isTrue();
        assertThat(setOps.difference(key, otherKey)).containsAll(Sets.newHashSet("1", "2"));
        assertThat(setOps.intersect(key, otherKey)).containsAll(Sets.newHashSet("3"));
        assertThat(setOps.differenceAndStore(key, otherKey, destKey)).isEqualTo(2L);
        assertThat(setOps.intersectAndStore(key, otherKey, destKey)).isEqualTo(1L);
        assertThat(setOps.unionAndStore(key, otherKey, destKey)).isEqualTo(5L);
    }

    /**
     * 순서 O, value 중복 X
     * */
    @Test
    public void operateZSet() {
        ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();
    }

    @Test
    public void operateGeo() {
        GeoOperations<String, Object> geoOps = redisTemplate.opsForGeo();
    }

    @Test
    public void operateHyperLogLog() {
        HyperLogLogOperations<String, Object> hyperLogLogOps = redisTemplate.opsForHyperLogLog();
    }
}
