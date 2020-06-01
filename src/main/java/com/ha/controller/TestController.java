package com.ha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    @GetMapping
    public Mono<String> getKey() {
        return reactiveRedisTemplate.opsForValue().get("testKey");
    }

    @PutMapping
    public Mono<Boolean> setKey() {
        return reactiveRedisTemplate.opsForValue().set("testKey", "testValue");
    }
}
