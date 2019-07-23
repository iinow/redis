package com.ha.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@RedisHash(value = "address")
public class Address implements Serializable {
	private static final long serialVersionUID = 2745953726985919768L;

	@Id
	private String id;
	private String addr;
	private Integer postnum;
}
