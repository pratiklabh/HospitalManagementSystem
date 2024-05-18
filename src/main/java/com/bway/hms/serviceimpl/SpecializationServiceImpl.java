package com.bway.hms.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bway.hms.model.Specialization;
import com.bway.hms.repository.SpecializationRepository;
import com.bway.hms.service.SpecializationService;

@Service
public class SpecializationServiceImpl implements SpecializationService {

	
	@Autowired
	private SpecializationRepository specRepo;
	
	@Override
	public void addSpecialization(Specialization specialization) {
		specRepo.save(specialization);
		
	}

	@Override
	public void deleteSpecialization(int id) {
		specRepo.deleteById(id);
		
	}

	@Override
	public void updateSpecialization(Specialization specialization) {
		specRepo.save(specialization);
		
	}

	@Override
	public Specialization getSpecializationById(int id) {
		return specRepo.findById(id).get();
		
	}

	@Override
	public List<Specialization> getAllSpecialization() {
		
		return specRepo.findAll();
	}

	@Override
	public boolean codeExists(int code) {
		
		return specRepo.existsByCode(code);
	}

	@Override
	public boolean nameExists(String name) {
		return specRepo.existsByName(name);
	}

}
