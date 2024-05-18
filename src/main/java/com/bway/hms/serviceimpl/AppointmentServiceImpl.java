package com.bway.hms.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bway.hms.model.Appointment;
import com.bway.hms.repository.AppointmentRepository;
import com.bway.hms.service.AppointmentService;


@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepo;

	@Override
	public void addAppointment(Appointment apt) {

		Appointment appointment = appointmentRepo.findById(apt.getId());
		if (appointment != null && appointment.getSlots() > 0) {
            appointment.setSlots(appointment.getSlots() - 1);
            appointmentRepo.save(apt);
        }
		
	}

	@Override
	public int getAvailableSlots() {
		
		
		// Get all appointments from the database
	    List<Appointment> appointments = appointmentRepo.findAll();
	    
	    // Calculate the total number of booked slots
	    int totalBookedSlots = appointments.size(); // Each appointment represents one booked slot
	    
	    // Calculate the number of available slots
	    int totalSlots = 10; // Total number of slots available initially
	    int availableSlots = totalSlots - totalBookedSlots;
	    
	    return availableSlots;
	}

	@Override
	public List<Appointment> getAllAppointments() {
		return appointmentRepo.findAll();
	}

	@Override
	public void approveAppointment(int id) {
		
		
	}

	@Override
	public Appointment getAppointmentById(int id) {
		
		return appointmentRepo.findById(id);
	}

	@Override
	public void updateAppointment(Appointment appointment) {
		appointmentRepo.save(appointment);
		
	}

	@Override
	public void deleteAppointment(int id) {
		appointmentRepo.deleteById(id);
		
	}

	
        
}
