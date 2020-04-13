package com.ha.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@ToString
//value 값을 지정하지 않으면 해당 클래스의 패키지 경로로 들어가게 된다.
@RedisHash(value = "person")
public class Person implements Serializable {
	private static final long serialVersionUID = -2861272462918118911L;

	@Id
	private String id;
	private String name;
	
}
