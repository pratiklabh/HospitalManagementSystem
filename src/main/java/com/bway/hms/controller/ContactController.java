package com.bway.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bway.hms.utils.MailUtils;

import jakarta.servlet.http.HttpSession;

@Controller
public class ContactController {
	
	@Autowired
	private MailUtils mailutil;
	
	@GetMapping("/contact")
	public String getContact(HttpSession session, Model model) {
		
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
		
		return "ContactForm";
	}
	
	@PostMapping("/contact")
	public String postContact(@RequestParam String toEmail, @RequestParam String subject, @RequestParam String message) {
		
		mailutil.sendEmail(toEmail, subject, message);
		return "ContactForm";
	}

}
