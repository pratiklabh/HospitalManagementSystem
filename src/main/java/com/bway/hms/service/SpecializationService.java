package com.bway.hms.service;

import java.util.List;

import com.bway.hms.model.Specialization;

public interface SpecializationService {
	
	void addSpecialization(Specialization specialization);
	
	void deleteSpecialization(int id);

	void updateSpecialization(Specialization specialization);
	
	Specialization getSpecializationById(int id);
	
	List<Specialization> getAllSpecialization();
	
	boolean codeExists(int code);
	
	boolean nameExists(String name);	
}
