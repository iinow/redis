package com.ha.mapper;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.stereotype.Component;

import com.ha.entity.Person;

//@Component
public class HashMapping {

//	@Autowired
//	HashOperations<String, Object, Object> hashOperations;
//
//	HashMapper<Object, byte[], byte[]> mapper = new ObjectHashMapper();
//
//	public <T> void writeHash(String key, T t) {
//	  Map<byte[], byte[]> mappedHash = mapper.toHash(t);
//	  hashOperations.putAll(key, mappedHash);
//	}
//
//	public <T> T loadHash(String key, Class<T> classTemplate) {
//	  Map<byte[], byte[]> loadedHash = hashOperations.entries("key");
//	  return (T) mapper.fromHash(loadedHash);
//	}
}
