package com.bway.hms.controller;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bway.hms.model.User;
import com.bway.hms.repository.UserRepository;
import com.bway.hms.service.UserService;
import com.bway.hms.utils.MailUtil;
import com.bway.hms.utils.MailUtils;

import jakarta.servlet.http.HttpSession;
import lombok.extern.java.Log;
@Log
@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private MailUtil mailUtil;
	
	
	@GetMapping("/")
	public String getChooseLogin() {
		
		return "ChooseLogin";
	
	}
	
	
	
	
	@GetMapping("/login")
	public String getLogin() {
		
		return "LoginForm";
	
	}
	@PostMapping("/login")
	public String postLogin(@ModelAttribute User user, Model model, HttpSession session) {
	    try {
	        // Hash the password
	        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));

	        // Attempt to log in the user
	        User usr = userService.userLogin(user.getEmail(), user.getPassword());

	        if (usr != null) {
	        	
	            if (usr.isEmailVerified()) {
	                // User found and verified
	            	log.info("-------Login Success---------");
	                session.setAttribute("validuser", usr);
	                session.setMaxInactiveInterval(120);  // Setting session to expire in 2 minutes

	                return "Home";
	                
	            } else {
	                // Email not verified
	            	log.info("-------Email not Verified---------");
	                model.addAttribute("message", "Email not verified. Please check your email for verification instructions.");
	                return "LoginForm";
	            }
	            
	            
	        } else {
	        	
	            // User not found, return error message
	        	log.info("-------User Not Found---------");
	            model.addAttribute("message", "User not Found!!!");
	            return "LoginForm";
	            
	        }
	        
	    } catch (Exception e) {
	        
	        model.addAttribute("message", "An error occurred. Please try again later.");
	        return "LoginForm";
	    }
	}

	
	@GetMapping("/signup")
	public String getSignup() {
		
		return "SignupForm";
	}
	
	@PostMapping("/signup")
	public String postSignup(@ModelAttribute User user, Model model) {
	    

		// to check if the email already exists
        User existingUser = userService.findByEmail(user.getEmail());
        
        
        if (existingUser != null) {
            
            model.addAttribute("error", "This email address already exists. Please use a different email.");
            return "SignupForm";
            
        }
		
		
		
		try {
	        // encrypting the password
	        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));

	        // to generate a 6-digit PIN
	        Random random = new Random();
	        int pin = random.nextInt(900000) + 100000;

	        // Set verification details
	        user.setVerificationPin(pin);
	        user.setEmailVerified(false);
	        user.setPinExpiration(LocalDateTime.now().plusHours(24)); // PIN expires in 24 hours

	        // to save user details to database
	        userService.userSignup(user);

	        // to send the verification email
	        mailUtil.sendEmail(user.getEmail(), pin);

	        model.addAttribute("user", user);

	        return "Verification";
	        
	        
	    } catch (Exception e) {
	    	
	        model.addAttribute("error", "An error occurred during signup. Please try again later.");
	        return "SignupForm"; 
	    }
	}
	
	
	
	
	@GetMapping("/verification")
	public String verify() {
		
		return "Verification";
	}
	
	@PostMapping("/verification")
	public String postVerify(@RequestParam String email, @RequestParam int pin, Model model) {
	    // Ensure this method correctly retrieves the user by email
		User user = userRepo.findByEmail(email);
		
		
	    // Check the PIN, the verification status, and whether the PIN has expired
	    if (user != null && user.getVerificationPin() == pin && !user.isEmailVerified() && LocalDateTime.now().isBefore(user.getPinExpiration())) {
	        
	    	user.setEmailVerified(true);
	        userService.userSignup(user);
	        
	        return "redirect:/login"; 
	        
	    } else {
	    	
	        model.addAttribute("error", "Invalid or expired PIN");
	        return "Verification"; 
	        
	    }
	}

	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		return"ChooseLogin";
	}
	

}
