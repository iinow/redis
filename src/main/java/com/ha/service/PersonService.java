package com.ha.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ha.entity.Person;
import com.ha.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository repository;
	
//	@Autowired
//	private RedisTemplate<Object, Object> template;
	
//	@Transactional
	public List<Person> findAllPerson(){
		List<Person> list = new ArrayList<>();
		repository.findAll().iterator().forEachRemaining(list::add);
		return list;
	}
	
//	@Transactional(rollbackFor = Exception.class)
	public Person insertPerson(Person person) throws Exception {
		
//		template.execute(new RedisCallback() {
//			@Override
//			public Object doInRedis(RedisConnection connection) throws DataAccessException {
//				connection.multi();
//				return null;
//			}
//		});
		Person s = repository.save(person);
		return s;
//		throw new Exception("test");
//		return repository.save(person);
	}
}
