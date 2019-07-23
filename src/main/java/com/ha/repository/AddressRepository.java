package com.ha.repository;

import org.springframework.data.repository.CrudRepository;

import com.ha.entity.Address;

public interface AddressRepository extends CrudRepository<Address, String>{

}
