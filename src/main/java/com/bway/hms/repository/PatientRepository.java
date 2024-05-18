package com.bway.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bway.hms.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer>{

	Patient findByEmailAndPassword(String email, String password);

	Patient findByEmail(String email);
	
}

