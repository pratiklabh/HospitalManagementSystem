package com.bway.hms.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bway.hms.model.Doctor;
import com.bway.hms.model.Patient;
import com.bway.hms.model.User;
import com.bway.hms.service.DoctorService;
import com.bway.hms.service.PatientService;
import com.bway.hms.service.UserService;
import com.bway.hms.utils.MailUtil;

import jakarta.servlet.http.HttpSession;

@Controller
public class ForgetPasswordController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private MailUtil mailutil;
	
	
	//for admin user forget password
	@GetMapping("/forgetPassword")
	public String getForgetPassword() { 
	    return "ForgetPasswordForm";
	}

	@PostMapping("/forgetPassword")
	public String postForgetPassword(@RequestParam String email, Model model, HttpSession FPsession) {
	    User user = userService.findByEmail(email);
	    Doctor doctor = doctorService.searchEmail(email);
	    Patient patient = patientService.searchEmail(email);
	    
	    
	    if(user == null && doctor==null && patient==null) {
	    	
	        model.addAttribute("error", "Provided Email Doesn't Exist !!!");
	        return "ForgetPasswordForm";
	    }
	    
	    Random random = new Random();
	    int vCode = random.nextInt(900000) + 100000;
	    
	    FPsession.setAttribute("sessionCode", vCode);
	    FPsession.setAttribute("email", email);
	    
	    mailutil.sendEmail(email, vCode);
	    
	    model.addAttribute("message", "The verification code has been sent to your email.");
	    return "VerificationForm";
	}

	@PostMapping("/verificationCode")
	public String postVerification(@RequestParam int userCode, Model model, HttpSession FPsession) {
	    Integer sessionCode = (Integer) FPsession.getAttribute("sessionCode");

	    if (sessionCode != null && sessionCode.equals(userCode)) {
	        model.addAttribute("message", "Verification successful. You can now reset your password.");
	        return "ResetPasswordForm";
	    } else {
	        model.addAttribute("error", "Invalid verification code!");
	        return "VerificationForm";
	    }
	}

	
	@PostMapping("/resetPassword")
	public String postResetPassword(HttpSession FPsession,@RequestParam String newPassword,Model model) {
		
		
		String userEmail = (String) FPsession.getAttribute("email");

        if (userEmail != null) {
    		User user = userService.findByEmail(userEmail);
    		Doctor doctor = doctorService.searchEmail(userEmail);
    	    Patient patient = patientService.searchEmail(userEmail);
    		
           
            if (user != null) {
            		user.setPassword(DigestUtils.md5DigestAsHex(newPassword.getBytes()));
            		userService.updateUser(user);  
                    model.addAttribute("message", "Password reset successfully.");
                    return "ChooseLogin";
            }else if(doctor!=null){
            	
            	doctor.setPassword(DigestUtils.md5DigestAsHex(newPassword.getBytes()));
        		doctorService.updateDoctor(doctor);  
                model.addAttribute("message", "Password reset successfully.");
                return "ChooseLogin";
            	
            } else if(patient!=null){
            	
            	patient.setPassword(DigestUtils.md5DigestAsHex(newPassword.getBytes()));
        		patientService.updatePatient(patient);  
                model.addAttribute("message", "Password reset successfully.");
                return "ChooseLogin";
            	
            }else {
                    model.addAttribute("error", "Passwords do not match!");
                    return "PasswordResetForm";
                }
            }
        FPsession.invalidate(); // Clear the session attributes
        return "redirect:/"; // Redirect to the login page
	}	
}
