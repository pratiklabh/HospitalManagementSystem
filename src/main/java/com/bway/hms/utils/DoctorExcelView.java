package com.bway.hms.utils;

import java.util.List;
import java.util.Map;



import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.bway.hms.model.Doctor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DoctorExcelView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(
			Map<String, Object> model, 
			Workbook workbook, 
			HttpServletRequest request,
			HttpServletResponse response) 
					throws Exception {

		//1. define your own excel file name
		response.addHeader("Content-Disposition", "attachment;filename=doctor.xls");
		
		//2. read data given by Controller
		@SuppressWarnings("unchecked")
		List<Doctor> list = (List<Doctor>) model.get("dList");
		
		//3. create one sheet
		Sheet sheet = workbook.createSheet("Doctor");
		
		//4. create row#0 as header
		setHead(sheet);
		
		//5. create row#1 onwards from List<T> 
		setBody(sheet,list);
	}

	private void setHead(Sheet sheet) {
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("DOCTOR NAME");
		row.createCell(2).setCellValue("DOCTOR EMAIL");
		row.createCell(3).setCellValue("DOCTOR PHONE");
		row.createCell(4).setCellValue("Specialization");
	}
	
	private void setBody(Sheet sheet, List<Doctor> list) {
		int rowNum = 1;
		for(Doctor spec : list) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(spec.getId());
			row.createCell(1).setCellValue(spec.getName());
			row.createCell(2).setCellValue(spec.getEmail());
			row.createCell(3).setCellValue(spec.getMobile());
			row.createCell(4).setCellValue(spec.getSpecialization().getName());
		}
	}

}
