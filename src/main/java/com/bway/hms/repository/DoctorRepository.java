package com.bway.hms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bway.hms.model.Doctor;
import com.bway.hms.model.Specialization;

public interface DoctorRepository extends JpaRepository<Doctor, Integer>{

	List<Doctor> findDoctorsByNameAndSpecialization(String name, Specialization specialization);
	
	Doctor findByEmailAndPassword(String email, String password);
	
	Doctor findByEmail(String email);
}
