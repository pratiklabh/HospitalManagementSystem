package com.bway.hms.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bway.hms.model.Patient;
import com.bway.hms.repository.PatientRepository;
import com.bway.hms.service.PatientService;


@Service
public class PatientServiceImpl implements PatientService{

	@Autowired
	private PatientRepository patientRepo;
	
	@Override
	public void addPatient(Patient patient) {
		patientRepo.save(patient);
		
	}

	@Override
	public void deletePatient(int id) {
		patientRepo.deleteById(id);
	}

	@Override
	public void updatePatient(Patient patient) {
		patientRepo.save(patient);
		
	}

	@Override
	public Patient getPatientById(int id) {
		
		return patientRepo.findById(id).get();
	}

	@Override
	public List<Patient> getAllPatients() {
		return patientRepo.findAll();
	}

	@Override
	public Patient searchPatient(String email, String password) {
		return patientRepo.findByEmailAndPassword(email, password);
	}

	@Override
	public Patient searchEmail(String email) {
		return patientRepo.findByEmail(email);
	}

}
