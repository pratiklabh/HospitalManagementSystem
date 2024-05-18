package com.bway.hms.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

@Controller
public class UploadController {

	@GetMapping("/upload")
	public String getUpload(HttpSession session, Model model) {

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

		return "UploadForm";
	}

	@PostMapping("/upload")
	public String postUpload(@RequestParam MultipartFile image, Model model) {

		if (!image.isEmpty()) {
			try {

				Files.copy(image.getInputStream(),
						Path.of("src/main/resources/static/image/" + image.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);

				model.addAttribute("message", "file uploaded successfully");
				return "UploadForm";

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		model.addAttribute("message", "file upload failed");
		return "UploadForm";
	}

}
