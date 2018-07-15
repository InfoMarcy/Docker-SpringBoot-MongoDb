package com.luxmart.security.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.luxmart.security.domain.User;

@Repository("UserRepository")
public interface UserRepository extends MongoRepository<User, String>{

	 // find user by UserName
	 User findByUsername(String username);
	 // find user by email
	 User findByEmail(String email);
	
	
}
