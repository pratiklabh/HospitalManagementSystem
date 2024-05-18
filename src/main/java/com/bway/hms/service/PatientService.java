package com.bway.hms.service;

import java.util.List;

import com.bway.hms.model.Patient;



public interface PatientService {
	
	void addPatient(Patient patient);
	
	void deletePatient(int id);
	
	void updatePatient(Patient patient);
	
	Patient getPatientById(int id);
	
	List<Patient> getAllPatients();

	Patient searchPatient(String email, String password);
	
	Patient searchEmail(String email);
}
