package com.bway.hms.service;

import com.bway.hms.model.User;

public interface UserService {
	
	void userSignup(User user);
	
	User userLogin(String email, String psw);
	
	User findByEmail(String email);
	
	void updateUser(User user);

}
