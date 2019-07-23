package com.ha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ha.repository.AddressRepository;

@Service
public class AddressService {

	@Autowired
	private AddressRepository repository;
}
