package com.bway.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bway.hms.model.Specialization;
import com.bway.hms.service.SpecializationService;

import jakarta.servlet.http.HttpSession;




@Controller
public class SpecializationController {

	@Autowired
	private SpecializationService specService;

	// To open specialization form
	@GetMapping("/specialization")
	public String getSpecialization(HttpSession session, Model model) {
	    
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

	    return "SpecializationForm";
	}


	// to store specialization data in database
	@PostMapping("/specialization")
	public String postSpecialization(@ModelAttribute Specialization specialization, Model model) {

		if (specService.codeExists(specialization.getCode()) || specService.nameExists(specialization.getName())) {
	           
			model.addAttribute("error", "Specialization with this code or name already exists.");
	        return "SpecializationForm";
	        
		}
		
	    specService.addSpecialization(specialization);
	    model.addAttribute("success", "Specialization added successfully!");
	    
	    return "SpecializationForm";
	}

	// to open specialization list
	@GetMapping("/specializationList")
	public String getspecializationList(Model model, HttpSession session) {

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
		return "SpecializationList";
	}

	
	
	
	// to delete a specialization

	@GetMapping("/specialization/delete")
	public String deleteSpecialization(@RequestParam int id, HttpSession session, Model model) {

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
		
		specService.deleteSpecialization(id);
		return "redirect:/specializationList";
	}

	// to edit specialization list
	@GetMapping("/specialization/edit")
	public String editspecialization(@RequestParam int id, Model model, HttpSession session) {

		
	    if (session.getAttribute("validuser") == null) {
	    	
	        // Check if validdoctor or validpatient session attribute is present
	        if (session.getAttribute("validdoctor") != null || session.getAttribute("validpatient") != null) {
	            model.addAttribute("errorMessage", "You are not authorized to perform this action.");
	            return "ErrorMessage";
	        } else {
	            return "ChooseLogin"; 
	        }
	    }
		model.addAttribute("sModel", specService.getSpecializationById(id));
		return "SpecializationEditForm";
	}
	
	
	//to update specialization details
	@PostMapping("/specialization/update")
	public String updateSpecialization(@ModelAttribute Specialization specialization ) {
		
		specService.updateSpecialization(specialization);
		return "redirect:/specializationList";
	}

}
