package com.bway.hms.service;

import java.util.List;

import com.bway.hms.model.Doctor;
import com.bway.hms.model.Specialization;

public interface DoctorService {
	
	void addDoctor(Doctor doctor);
	
	void deleteDoctor(int id);
	
	void updateDoctor(Doctor doctor);
	
	Doctor getDoctorById(int id);
		
	List<Doctor> searchDoctors(String name, Specialization specialization);
	
	List<Doctor> getAllDoctors();

	Doctor doctorLogin(String email, String password);
	
	Doctor searchEmail(String email);

}
