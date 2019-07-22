package com.ha.repository;

import org.springframework.data.repository.CrudRepository;

import com.ha.entity.Person;

public interface PersonRepository extends CrudRepository<Person, String>{

}
