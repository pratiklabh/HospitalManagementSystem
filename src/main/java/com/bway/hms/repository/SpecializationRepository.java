package com.bway.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bway.hms.model.Specialization;

public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {

	
	boolean existsByCode(int code);
	
	boolean existsByName(String name);
	
	
}
