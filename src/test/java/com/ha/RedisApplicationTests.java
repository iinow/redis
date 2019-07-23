package com.ha;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.test.context.junit4.SpringRunner;

import com.ha.entity.Address;
import com.ha.entity.Person;
import com.ha.repository.AddressRepository;
import com.ha.service.PersonService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisApplicationTests {

//	@Autowired
//	PersonRepository repository;
	
	@Autowired
	PersonService service;
	
	@Autowired
	AddressRepository addressRepository;
	
//	@Autowired
//	RedisTemplate<String, Object> template;
	
	@Test
	public void contextLoads() {
//		Person p = new Person();
//		p.setName("My name is...");
//		p.setId("ha");
//		repository.save(p);
//		
//		Person res = repository.findById("ha").get();
//		assertEquals(res.getId(), "ha");
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
	
	/*
	 * @Test public void add_Template() throws Exception { template.execute(new
	 * RedisCallback<Object>() {
	 * 
	 * @Override public Object doInRedis(RedisConnection conn) throws
	 * DataAccessException { conn.subscribe(new MessageListener() {
	 * 
	 * @Override public void onMessage(Message message, byte[] pattern) {
	 * System.out.println("message: "+message.toString()+", pattern: "+pattern);
	 * if(message.toString().equals("ch2")) { //
	 * conn.getSubscription().subscribe("ch2".getBytes()); }else
	 * if(message.toString().equals("END")) { conn.getSubscription().unsubscribe();
	 * } } }, "ch1".getBytes());
	 * 
	 * conn.getSubscription().subscribe("ch2".getBytes()); return null; } });
	 * Thread.sleep(1000000000); // Person p = new Person(); // p.setId("20190723");
	 * // p.setName("joo2"); // service.insertPerson(p); // template.opsForHash().pu
	 * List<RedisClientInfo> list = template.getClientList();
	 * System.out.println(list.size()); System.out.println(list.toString());
	 * System.out.println(template.keys("*").toString());
	 * System.out.println(template.opsForHash().get("person:20190723", "id"));
	 * System.out.println("dd"+template.opsForHash().values("person:20190723").
	 * toString()); }
	 */
	
	/*
	 * @Resource(name = "redisTemplate") ValueOperations<String, String>
	 * valueOperations;
	 * 
	 * @Resource(name = "redisTemplate") SetOperations<Object, Object>
	 * setOperations;
	 * 
	 * @Resource(name="redisTemplate") private HashOperations<String, String,
	 * String> hashOperations;
	 */

	@Autowired
	RedisTemplate<String, Address> redisTemplate;
	
	@Test
	public void template() {
		
//		Set<Object> set = setOperations.members("people");
//		set.iterator().forEachRemaining(System.out::println);
//		System.out.println(set.iterator().toString());
		Address addr = new Address();
		addr.setAddr("ouya");
		addr.setId("12");
		addr.setPostnum(110101);
		
		HashOperations<String, String, Address> oper = redisTemplate.opsForHash();
		oper.put("HI", addr.getId(), addr);
		
		
//		System.out.println(hashOperations.entries("people:ha").toString());
	}
	
	public static void main(String[] args) {
		String str = null;
		Optional.ofNullable(str).orElseThrow();
	}
}
