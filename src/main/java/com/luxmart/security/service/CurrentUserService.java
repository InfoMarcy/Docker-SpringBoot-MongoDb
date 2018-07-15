package com.luxmart.security.service;

import org.springframework.stereotype.Service;

import com.luxmart.security.domain.CurrentUser;
import com.luxmart.security.domain.Role;


@Service
public class CurrentUserService {

	public boolean canAccessUser(CurrentUser currentUser, String userId) {
        return currentUser != null
                && (currentUser.getRole() == Role.ROLE_ADMIN || currentUser.getId().equals(userId));
    }
}