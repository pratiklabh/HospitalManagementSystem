package com.bway.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bway.hms.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	User findByEmailAndPassword(String email, String psw);
	User findByEmail(String email);
}
