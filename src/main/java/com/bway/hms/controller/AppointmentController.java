package com.bway.hms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bway.hms.model.Appointment;
import com.bway.hms.model.Doctor;
import com.bway.hms.model.Patient;
import com.bway.hms.model.Specialization;
import com.bway.hms.repository.AppointmentRepository;
import com.bway.hms.service.AppointmentService;
import com.bway.hms.service.DoctorService;
import com.bway.hms.service.PatientService;
import com.bway.hms.service.SpecializationService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import jakarta.servlet.http.HttpSession;

@Controller
public class AppointmentController {
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private SpecializationService specService;
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private AppointmentRepository appointmentRepo;
	
	//to open make apppointment form
	@GetMapping("/appointment")
	public String getAppointment(@ModelAttribute Appointment appointment,Model model, HttpSession session) {
		
		
		// Check if the validuser or validpatient session attribute is present
	    if (session.getAttribute("validuser") == null && session.getAttribute("validpatient") == null) {
	        // Check if validdoctor session attribute is present
	        if (session.getAttribute("validdoctor") != null) {
	            model.addAttribute("errorMessage", "You are not authorized to perform this action.");
	            return "ErrorMessage";
	        } else {
	            return "ChooseLogin"; // Redirect to login form if no valid session is found
	        }
	    }
		
		model.addAttribute("pList", patientService.getAllPatients());
		model.addAttribute("dList", doctorService.getAllDoctors());
		
		int availableSlots = appointmentService.getAvailableSlots();
		model.addAttribute("availableSlots", availableSlots);
		
		appointmentService.addAppointment(appointment);
			
		return "MakeAppointmentForm";
	
		
	}
	
	@PostMapping("/appointment")
	public String postAppointment(@ModelAttribute Appointment apt, Model model) {
		
		if(appointmentService.getAvailableSlots() == 0) {
			model.addAttribute("message", "No slots available");
			return "SlotUnavailable";
		}
		appointmentRepo.save(apt);
		return "redirect:/appointment";
	}
	
	
	
	
	//to view appointment status
	@GetMapping("/viewAppointmentStatus")
	public String viewAppointmentStatus(Model model, HttpSession session) {
	    
	    if (session.getAttribute("validuser") == null 
	    		&& session.getAttribute("validdoctor") == null
	    			&& session.getAttribute("validpatient") == null) {
	        return "ChooseLogin";
	    }
	    
	    // Add the list of appointments to the model
	    model.addAttribute("aList", appointmentService.getAllAppointments());
	    return "ViewAppointmentStatusForm";
	}

	
	

	//to delete an apppointment
	@GetMapping("/appointment/delete")
	public String deleteAppointment(@RequestParam int id, HttpSession session, Model model) {
		
		// Check if the validuser or validpatient session attribute is present
	    if (session.getAttribute("validuser") == null && session.getAttribute("validdoctor") == null) {
	        // Check if validdoctor session attribute is present
	        if (session.getAttribute("validpatient") != null) {
	            model.addAttribute("errorMessage", "You are not authorized to perform this action.");
	            return "ErrorMessage";
	        } else {
	            return "ChooseLogin"; // Redirect to login form if no valid session is found
	        }
	    }
		
		appointmentRepo.deleteById(id);
		return "redirect:/viewAppointmentStatus";
	}
	
	
	
	//to approve appointment status
	@GetMapping("/approveAppointment")
	public String approveAppointment(Model model,HttpSession session) {
		
		// Check if the validuser or validpatient session attribute is present
	    if (session.getAttribute("validuser") == null && session.getAttribute("validdoctor") == null) {
	        // Check if validdoctor session attribute is present
	        if (session.getAttribute("validpatient") != null) {
	            model.addAttribute("errorMessage", "You are not authorized to perform this action.");
	            return "ErrorMessage";
	        } else {
	            return "ChooseLogin"; // Redirect to login form if no valid session is found
	        }
	    }
		
		model.addAttribute("aList", appointmentService.getAllAppointments());
		return "ApproveAppointmentForm";
	}
	
	
	@PostMapping("/acceptAppointment")
	public String acceptAppointment(@RequestParam int id, Model model) {
		
		Appointment appt = appointmentService.getAppointmentById(id);
		
		appt.setApproved(true);
		
		appointmentService.updateAppointment(appt);
		return "redirect:/viewAppointmentStatus";
	}
	
	
	@PostMapping("/rejectAppointment")
	public String rejectAppointment(@RequestParam int id, Model model) {
		
		Appointment appt = appointmentService.getAppointmentById(id);
		
		appt.setApproved(false);
		
		appointmentService.updateAppointment(appt);
		return "redirect:/viewAppointmentStatus";
	}
	
	
	
	//to open search and book appointment form

	@GetMapping("/searchAndBookAppointment")
	public String searchAndBook(Model model,HttpSession session) {
		
		// Check if the validuser or validpatient session attribute is present
	    if (session.getAttribute("validuser") == null && session.getAttribute("validpatient") == null) {
	        // Check if validdoctor session attribute is present
	        if (session.getAttribute("validdoctor") != null) {
	            model.addAttribute("errorMessage", "You are not authorized to perform this action.");
	            return "ErrorMessage";
	        } else {
	            return "ChooseLogin"; // Redirect to login form if no valid session is found
	        }
	    }
		
		model.addAttribute("pList", patientService.getAllPatients());
		model.addAttribute("dList", doctorService.getAllDoctors());
		model.addAttribute("sList", specService.getAllSpecialization());
		
		return "SearchAndBookAppointmentForm";
	}
	

	
	
	
	@PostMapping("/searchAndBookAppointment")
	public String searchAndBook(@RequestParam int doctorname, @RequestParam Specialization specialization,@RequestParam int patientname ,Model model) {
	    
		Doctor doctorDetails = doctorService.getDoctorById(doctorname);
	    Patient patientDetails = patientService.getPatientById(patientname);
	    
		model.addAttribute("patientname", patientDetails.getName());
		model.addAttribute("specialization", specialization);
	    model.addAttribute("doctorname", doctorDetails.getName());
	    model.addAttribute("mobile", doctorDetails.getMobile());
	    model.addAttribute("email", doctorDetails.getEmail());
	    
	    
	    List<Doctor> doctors = doctorService.searchDoctors(doctorDetails.getName(), specialization);
	    model.addAttribute("doctors", doctors);
	    
	    int availableSlots = appointmentService.getAvailableSlots();
		model.addAttribute("availableSlots", availableSlots);
		
	    
	    
	    return "SearchAppointmentResultsForm";
	}
	
	
	

	
}
