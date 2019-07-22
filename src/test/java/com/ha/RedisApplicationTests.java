package com.ha;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ha.entity.Person;
import com.ha.repository.PersonRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisApplicationTests {

	@Autowired
	PersonRepository repository;
	
	@Test
	public void contextLoads() {
		Person p = new Person();
		p.setName("My name is...");
		p.setId("id!!!");
		repository.save(p);
		repository.findAll().forEach(p2->{
			System.out.println(p2.toString());
		});
	}

}
