package com.bway.hms.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bway.hms.model.User;
import com.bway.hms.repository.UserRepository;
import com.bway.hms.service.UserService;



@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public void userSignup(User user) {
		userRepo.save(user);
		
	}

	@Override
	public User userLogin(String email, String psw) {
		return userRepo.findByEmailAndPassword(email, psw);
		
	}

	@Override
	public User findByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public void updateUser(User user) {
		userRepo.save(user);
	}

}
