package com.ha.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RequestMapping(value = "/cache")
@RestController
public class CacheController {

    @GetMapping
    @Cacheable(value = "USER", key = "#name", unless = "#result == null")
    public String hello(
            @RequestParam(name = "name") String name
    ) {
        log.info("not register cache data, {}", name);
        return UUID.randomUUID().toString();
    }

    @GetMapping(value = "/world")
    @CachePut(value = "USER", key = "#name", unless = "#result == null")
    public String world(
            @RequestParam(name = "name") String name
    ) {
        log.info("not register cache data, {}", name);
        return UUID.randomUUID().toString();
    }

    @GetMapping(value = "/delete")
    @CacheEvict(value = "USER", key = "#name")
    public String delete(
            @RequestParam(name = "name") String name
    ) {
        log.info("not register cache data, {}", name);
        return UUID.randomUUID().toString();
    }
}
