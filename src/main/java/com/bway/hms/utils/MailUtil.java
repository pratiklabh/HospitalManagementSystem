package com.bway.hms.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailUtil {
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	public void sendEmail(String toEmail, int pin) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(toEmail);
        msg.setSubject("Verification Email");
        msg.setText("Your OTP is "+pin);

        javaMailSender.send(msg);

    }
}
