package com.bway.hms.service;

import java.util.List;

import com.bway.hms.model.Appointment;

public interface AppointmentService {
	
	void addAppointment(Appointment appointment);

	int getAvailableSlots();
	
	List<Appointment> getAllAppointments();
	
	Appointment getAppointmentById(int id);
	
	void approveAppointment(int id);
	
	void updateAppointment(Appointment appointment);
	
	void deleteAppointment(int id);
	
	
}

