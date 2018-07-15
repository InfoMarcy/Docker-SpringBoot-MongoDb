package com.luxmart.security.service.impl;

import java.security.Principal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luxmart.security.domain.Domicilio;
import com.luxmart.security.domain.User;
import com.luxmart.security.repository.UserRepository;
import com.luxmart.security.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	// connect to the user repository
	@Autowired
	UserRepository userRepository;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;



	public User create(User user) {
		// check if the user exist
		User localUser = userRepository.findByUsername(user.getUsername());
		// if user not exist
		if (localUser != null) {// if user does not exist
			LOG.info("User with username {} already exist. Nothing will be done. ", user.getUsername());
		} else {// if user exist
			String encryptedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encryptedPassword);


			// save the new user
			localUser = userRepository.save(user);
		}

		// return the user
		return localUser;
	}

	// find user by email
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	// find user by username
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	// check if the user already exist
	public boolean checkUserExist(String username, String email) {
		if (checkUsernameExists(username) || checkEmailExists(email)) {
			return true;
		} else {
			return false;
		}

	}

	// check if the username exist
	public boolean checkUsernameExists(String username) {
		if (null != findByUsername(username)) {
			return true;
		} else {
			return false;
		}
	}

	// check if the email exist
	public boolean checkEmailExists(String email) {
		if (null != findByEmail(email)) {
			return true;
		} else {
			return false;
		}
	}


	// save the user
	@Override
	public void save(User user) {
		userRepository.save(user);

	}

	// find all users
	@Override
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}

	public void enableUser(String username) {
		User user = findByUsername(username);
		user.setEnabled(true);
		userRepository.save(user);
	}

	public void disableUser(String username) {
		User user = findByUsername(username);
		user.setEnabled(false);
		userRepository.save(user);
		System.out.println(username + " is disabled.");
	}

	@Override
	public void saveDomicilio(Domicilio domicilio, Principal principal) {
		// get the current user
				User user = this.findByEmail(principal.getName());

				// iterate over the recipients
				for (Domicilio item : user.getDomicilios()) {

					// check if the recipient is the one we are looking for
					if (item.getId().toString().equals(domicilio.getId().toString())) {

						// remove the recipient from the class
						user.getDomicilios().remove(item);
						// add the recipient to the class
						user.getDomicilios().add(domicilio);
						// save the recipient
						userRepository.save(user);
					}

				}

				// add a recipient to the user
				user.getDomicilios().add(domicilio);
				// save the recipient
				userRepository.save(user);
	}
	
	
	// delete a recipient by name
	@Override
	public void deleteDomicilioById(String domicilio, Principal principal) {
		// get the current user
		User user = this.findByEmail(principal.getName());

		Optional<Domicilio> deleteDomicilio = user.getDomicilios().stream() // convert list to stream
				.filter(dom -> domicilio.equals(dom.getId())) // filter the recipient by name
				.findFirst(); // get the first match

		// check if the recipient is not null
		if (deleteDomicilio.get() != null) {
			// remove the recipient from the class
			user.getDomicilios().remove(deleteDomicilio.get());
			// update and save the changes
			userRepository.save(user);
		}

	}
	

}
