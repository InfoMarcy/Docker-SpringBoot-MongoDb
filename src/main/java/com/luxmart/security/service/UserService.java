package com.luxmart.security.service;

import java.security.Principal;

import com.luxmart.security.domain.Domicilio;
import com.luxmart.security.domain.User;

public interface UserService {
	
	User findByUsername(String username);
	User findByEmail(String email);
	boolean checkUserExist(String username, String email);
	boolean checkUsernameExists(String username);
	boolean checkEmailExists(String email);

	User create(User user);
	
	void save(User user);
	Iterable<User> findAll();
	void enableUser(String username);
	void disableUser(String username);
	
	void saveDomicilio(Domicilio domicilio, Principal principal);
	void deleteDomicilioById(String domicilio, Principal principal);

}
