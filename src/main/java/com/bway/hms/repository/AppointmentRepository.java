package com.bway.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bway.hms.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer>{

	Appointment findById(int id);
		
}
