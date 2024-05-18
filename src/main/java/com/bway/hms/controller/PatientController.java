package com.bway.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bway.hms.model.Doctor;
import com.bway.hms.model.Patient;
import com.bway.hms.service.DoctorService;
import com.bway.hms.service.PatientService;
import com.bway.hms.utils.PatientExcelView;
import com.bway.hms.utils.PatientPdfView;

import jakarta.servlet.http.HttpSession;

@Controller
public class PatientController {

	@Autowired
	private PatientService patientService;

	@Autowired
	private DoctorService doctorService;
	
	@GetMapping("/patientLogin")
	public String getPatientLogin() {
		
		return "PatientLoginForm";
	}
	
	@PostMapping("/patientLogin")
	public String postPatientLogin(@ModelAttribute Patient patient, HttpSession session, Model model) {
		
		try {
	        // Hash the password
	        patient.setPassword(DigestUtils.md5DigestAsHex(patient.getPassword().getBytes()));

	        // Attempt to log in the user
	        Patient pat = patientService.searchPatient(patient.getEmail(), patient.getPassword());

	        if (pat != null) {
	        
	            session.setAttribute("validpatient", pat);
	            session.setMaxInactiveInterval(120);  // Setting session to expire in 2 minutes

	             return "Home";
	                
	        } else {
	        	
	            model.addAttribute("error", "Patient not Found!!!");
	            return "PatientLoginForm";
	            
	        }
	        
	    } catch (Exception e) {
	        
	        model.addAttribute("message", "An error occurred. Please try again later.");
	        return "PatientLoginForm";
	    }
		
	}
	
	
	
	
	
	

	// to add patient
	@GetMapping("/patient")
	public String getPatient(Model model, HttpSession session) {

		// Check if the validuser session attribute is present
	    if (session.getAttribute("validuser") == null) {
	        // Check if validdoctor or validpatient session attribute is present
	        if (session.getAttribute("validdoctor") != null || session.getAttribute("validpatient") != null) {
	            model.addAttribute("errorMessage", "You are not authorized to perform this action.");
	            return "ErrorMessage";
	        } else {
	            return "ChooseLogin"; // Redirect to login form if no valid session is found
	        }
	    }

		model.addAttribute("dList", doctorService.getAllDoctors());
		return "PatientForm";
	}

	@PostMapping("/patient")
	public String postPatient(@ModelAttribute Patient patient) {

		patient.setPassword(DigestUtils.md5DigestAsHex(patient.getPassword().getBytes()));
		patientService.addPatient(patient);
		return "redirect:/patient";
	}

	// to get patient list
	@GetMapping("/patientList")
	public String getPatients(Model model, HttpSession session) {
		
		// Check if the validuser session attribute is present
	    if (session.getAttribute("validuser") == null) {
	        // Check if validdoctor or validpatient session attribute is present
	        if (session.getAttribute("validdoctor") != null || session.getAttribute("validpatient") != null) {
	            model.addAttribute("errorMessage", "You are not authorized to perform this action.");
	            return "ErrorMessage";
	        } else {
	            return "ChooseLogin"; // Redirect to login form if no valid session is found
	        }
	    }
		model.addAttribute("pList", patientService.getAllPatients());
		return "PatientListForm";

	}

	@PostMapping("/patientList")
	public String postPatients(Model model) {

		model.addAttribute("pList", patientService.getAllPatients());

		return "PatientListForm";

	}

	// delete patient data
	@GetMapping("/patient/delete")
	public String delete(@RequestParam int id, HttpSession session, Model model) {

		// Check if the validuser session attribute is present
	    if (session.getAttribute("validuser") == null) {
	        // Check if validdoctor or validpatient session attribute is present
	        if (session.getAttribute("validdoctor") != null || session.getAttribute("validpatient") != null) {
	            model.addAttribute("errorMessage", "You are not authorized to perform this action.");
	            return "ErrorMessage";
	        } else {
	            return "ChooseLogin"; // Redirect to login form if no valid session is found
	        }
	    }

		patientService.deletePatient(id);
		return "redirect:/patientList";
	}

	// to edit patient data
	@GetMapping("/patient/edit")
	public String edit(@RequestParam int id, Model model, HttpSession session) {

		// Check if the validuser session attribute is present
	    if (session.getAttribute("validuser") == null) {
	        // Check if validdoctor or validpatient session attribute is present
	        if (session.getAttribute("validdoctor") != null || session.getAttribute("validpatient") != null) {
	            model.addAttribute("errorMessage", "You are not authorized to perform this action.");
	            return "ErrorMessage";
	        } else {
	            return "ChooseLogin"; // Redirect to login form if no valid session is found
	        }
	    }
		model.addAttribute("pModel", patientService.getPatientById(id));
		return "PatientEditForm";

	}

	// to update edited patient data
	@PostMapping("/patient/update")
	public String update(@ModelAttribute Patient patient) {
		patient.setPassword(DigestUtils.md5DigestAsHex(patient.getPassword().getBytes()));

		patientService.updatePatient(patient);
		return "redirect:/patientList";

	}

	// to view full details of patient

	@GetMapping("/patient/details")
	public String viewDetails(@RequestParam int id, Model model, HttpSession session) {

		// Check if the validuser session attribute is present
	    if (session.getAttribute("validuser") == null) {
	        // Check if validdoctor or validpatient session attribute is present
	        if (session.getAttribute("validdoctor") != null || session.getAttribute("validpatient") != null) {
	            model.addAttribute("errorMessage", "You are not authorized to perform this action.");
	            return "ErrorMessage";
	        } else {
	            return "ChooseLogin"; // Redirect to login form if no valid session is found
	        }
	    }

		model.addAttribute("patient_details", patientService.getPatientById(id));
		return "PatientFullDetails";
	}

	// to download excel
	@GetMapping("/patient/excel")
	public ModelAndView excel() {

		ModelAndView mv = new ModelAndView();

		mv.addObject("pList", patientService.getAllPatients());
		mv.setView(new PatientExcelView());

		return mv;
	}

	// to download pdf
	@GetMapping("/patient/pdf")
	public ModelAndView pdf() {

		ModelAndView mv = new ModelAndView();

		mv.addObject("pList", patientService.getAllPatients());
		mv.setView(new PatientPdfView());

		return mv;
	}

}
