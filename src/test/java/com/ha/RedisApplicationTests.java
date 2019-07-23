package com.ha;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ha.entity.Person;
import com.ha.repository.PersonRepository;
import com.ha.service.PersonService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisApplicationTests {

	@Autowired
	PersonRepository repository;
	
	@Autowired
	PersonService service;
	
//	@Autowired
//	RedisTemplate<String, Object> template;
	
	@Test
	public void contextLoads() {
		Person p = new Person();
		p.setName("My name is...");
		p.setId("ha");
		repository.save(p);
		
		Person res = repository.findById("ha").get();
		assertEquals(res.getId(), "ha");
	}
	
	@Test
	public void get() {
		List<Person> list = service.findAllPerson();
		list.forEach(System.out::println);
	}

	@Test
	public void add() throws Exception {
		Person p = new Person();
		p.setId("seo2");
		p.setName("joo");
		service.insertPerson(p);
	}
	
	@Test
	public void add_Template() throws Exception {
		Person p = new Person();
		p.setId("20190723");
		p.setName("joo2");
		service.insertPerson(p);
//		template.opsForHash().pu
	}
	
	@Test
	public void template() {
//		System.out.println(template.opsForValue().get("people:seo2"));
	}
}
