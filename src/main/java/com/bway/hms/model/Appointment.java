package com.bway.hms.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name="appointment_tbl")
public class Appointment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String patientname;
	private String doctorname;
	private LocalDate date;
	private int slots = 10;
	private String details;
	private int fee;
	private boolean isApproved = false;
	
	
}
