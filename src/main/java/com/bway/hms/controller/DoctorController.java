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
import com.bway.hms.model.User;
import com.bway.hms.service.DoctorService;
import com.bway.hms.service.SpecializationService;
import com.bway.hms.utils.DoctorExcelView;
import com.bway.hms.utils.DoctorPdfView;

import jakarta.servlet.http.HttpSession;

@Controller
public class DoctorController {

	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private SpecializationService specService;

	//doctorLogin
	@GetMapping("/doctorLogin")
	public String getDoctorLogin() {
		
		return "DoctorLoginForm";
	}
	
	@PostMapping("/doctorLogin")
	public String postDoctorLogin(@ModelAttribute Doctor doctor, Model model,HttpSession session) {
		try {
	        //Hash the password
	        doctor.setPassword(DigestUtils.md5DigestAsHex(doctor.getPassword().getBytes()));

	        // Attempt to log in the user
	        Doctor doc = doctorService.doctorLogin(doctor.getEmail(), doctor.getPassword());

	        if (doc != null) {
	        
	            session.setAttribute("validdoctor", doc);
	            session.setMaxInactiveInterval(120);  // Setting session to expire in 2 minutes

	             return "Home";
	                
	        } else {
	        	
	            model.addAttribute("error", "User not Found!!!");
	            return "DoctorLoginForm";
	            
	        }
	        
	    } catch (Exception e) {
	        
	        model.addAttribute("message", "An error occurred. Please try again later.");
	        return "DoctorLoginForm";
	    }
		
	}
	
	
	
	
	
	
	// to open doctor form
	@GetMapping("/doctor")
	public String getDoctor(Model model, HttpSession session) {

		// Check if the validuser session attribute is present
	    if (session.getAttribute("validuser") == null) {
	        // Check if validdoctor or validpatient session attribute is present
	        if (session.getAttribute("validdoctor") != null || session.getAttribute("validpatient") != null) {
	            model.addAttribute("errorMessage", "You are not authorized to perform this action.");
	            return "ErrorMessage";
	        } else {
	            return "LoginForm"; // Redirect to login form if no valid session is found
	        }
	    }
		model.addAttribute("sList", specService.getAllSpecialization());
		return "DoctorForm";
	}

	// to add doctor data in db
	@PostMapping("/doctor")
	public String postDoctor(@ModelAttribute Doctor doctor) {

        doctor.setPassword(DigestUtils.md5DigestAsHex(doctor.getPassword().getBytes()));

		doctorService.addDoctor(doctor);
		return "redirect:/doctor";
	}

	// to get doctor list
	@GetMapping("/doctorList")
	public String getDoctorList(Model model, HttpSession session) {

		// Check if the validuser session attribute is present
	    if (session.getAttribute("validuser") == null) {
	        // Check if validdoctor or validpatient session attribute is present
	        if (session.getAttribute("validdoctor") != null || session.getAttribute("validpatient") != null) {
	            model.addAttribute("errorMessage", "You are not authorized to perform this action.");
	            return "ErrorMessage";
	        } else {
	            return "LoginForm"; // Redirect to login form if no valid session is found
	        }
	    }

		model.addAttribute("dList", doctorService.getAllDoctors());

		return "DoctorListForm";
	}

	@PostMapping("/doctorList")
	public String postDoctorList(Model model) {

		model.addAttribute("dList", doctorService.getAllDoctors());

		return "DoctorListForm";
	}

	// to delete a doctor data
	@GetMapping("/doctor/delete")
	public String delete(@RequestParam int id, HttpSession session, Model model) {

		// Check if the validuser session attribute is present
	    if (session.getAttribute("validuser") == null) {
	        // Check if validdoctor or validpatient session attribute is present
	        if (session.getAttribute("validdoctor") != null || session.getAttribute("validpatient") != null) {
	            model.addAttribute("errorMessage", "You are not authorized to perform this action.");
	            return "ErrorMessage";
	        } else {
	            return "LoginForm"; // Redirect to login form if no valid session is found
	        }
	    }

		doctorService.deleteDoctor(id);
		return "redirect:/doctorList";

	}

	// to edit a doctor data
	@GetMapping("/doctor/edit")
	public String edit(@RequestParam int id, Model model, HttpSession session) {

		// Check if the validuser session attribute is present
	    if (session.getAttribute("validuser") == null) {
	        // Check if validdoctor or validpatient session attribute is present
	        if (session.getAttribute("validdoctor") != null || session.getAttribute("validpatient") != null) {
	            model.addAttribute("errorMessage", "You are not authorized to perform this action.");
	            return "ErrorMessage";
	        } else {
	            return "LoginForm"; // Redirect to login form if no valid session is found
	        }
	    }
	    
		model.addAttribute("sList", specService.getAllSpecialization());
		model.addAttribute("dModel", doctorService.getDoctorById(id));
		return "DoctorEditForm";
	}

	// to update the edited data
	@PostMapping("/doctor/update")
	public String update(@ModelAttribute Doctor doctor) {
		doctorService.updateDoctor(doctor);
		return "redirect:/doctorList";
	}

	// to view full details of a particular doctor
	@GetMapping("/doctor/details")
	public String viewDetails(@RequestParam int id, Model model, HttpSession session) {

		// Check if the validuser session attribute is present
	    if (session.getAttribute("validuser") == null) {
	        // Check if validdoctor or validpatient session attribute is present
	        if (session.getAttribute("validdoctor") != null || session.getAttribute("validpatient") != null) {
	            model.addAttribute("errorMessage", "You are not authorized to perform this action.");
	            return "ErrorMessage";
	        } else {
	            return "LoginForm"; // Redirect to login form if no valid session is found
	        }
	    }

		model.addAttribute("doctor_details", doctorService.getDoctorById(id));
		return "DoctorFullDetails";
	}
	
	
	
	
	
	
	//to download excel
	@GetMapping("/doctor/excel")
	public ModelAndView excel() {
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("dList", doctorService.getAllDoctors());
		mv.setView(new DoctorExcelView());
		
		return mv;
	}
	
	
	//to download pdf
	@GetMapping("/doctor/pdf")
	public ModelAndView pdf() {
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("dList", doctorService.getAllDoctors());
		mv.setView(new DoctorPdfView());
		
		return mv;
	}
	
	

}
