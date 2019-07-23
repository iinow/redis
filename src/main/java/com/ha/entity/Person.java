package com.ha.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Data;
import lombok.ToString;

@Data
@ToString

//value ���� �������� ������ �ش� Ŭ������ ��Ű�� ��η� ���� �ȴ�.
@RedisHash(value = "person")
public class Person implements Serializable {
	private static final long serialVersionUID = -2861272462918118911L;

	@Id
	private String id;
	private String name;
	
}
