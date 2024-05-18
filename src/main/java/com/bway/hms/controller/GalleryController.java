package com.bway.hms.controller;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class GalleryController {

	@GetMapping("/gallery")
	public String gallery(Model model,HttpSession session) {

		if (session.getAttribute("validuser") == null 
	    		&& session.getAttribute("validdoctor") == null
	    			&& session.getAttribute("validpatient") == null) {
	        return "ChooseLogin";
	    }

		String[] imgList = new File("src/main/resources/static/image").list();
		model.addAttribute("imgList", imgList);

		return "Gallery";
	}

}
