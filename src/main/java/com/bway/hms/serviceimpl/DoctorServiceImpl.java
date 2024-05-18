package com.bway.hms.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bway.hms.model.Doctor;
import com.bway.hms.model.Specialization;
import com.bway.hms.repository.DoctorRepository;
import com.bway.hms.service.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService{

	@Autowired
	private DoctorRepository doctorRepo;
	
	@Override
	public void addDoctor(Doctor doctor) {
		doctorRepo.save(doctor);
		
	}

	@Override
	public void deleteDoctor(int id) {

		doctorRepo.deleteById(id);
		
	}

	@Override
	public void updateDoctor(Doctor doctor) {

		doctorRepo.save(doctor);
		
	}

	@Override
	public Doctor getDoctorById(int id) {
		
		return doctorRepo.findById(id).get();
		
	}

	@Override
	public List<Doctor> getAllDoctors() {

		return doctorRepo.findAll();
	}

	@Override
	public List<Doctor> searchDoctors(String name, Specialization specialization) {
		return doctorRepo.findDoctorsByNameAndSpecialization(name,specialization);
	}

	@Override
	public Doctor doctorLogin(String email, String password) {
		return doctorRepo.findByEmailAndPassword(email, password);
	}

	@Override
	public Doctor searchEmail(String email) {
		return doctorRepo.findByEmail(email);
	}



	
	

	
	
}
