package com.sanjaygoyaljpr.service;

import com.sanjaygoyaljpr.model.User;

public interface UserService {

	User findUserByEmail(String email);

	void saveUser(User user);

	User findOne(Long userId);
}
