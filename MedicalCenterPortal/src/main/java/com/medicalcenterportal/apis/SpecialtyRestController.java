package com.medicalcenterportal.apis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicalcenter.entities.Specialty;
import com.medicalcenter.services.SpecialtyService;


@RestController
@RequestMapping({"/apis/v1/specialtiesgrid", "/apis/test/specialtiesgrid"})
public class SpecialtyRestController {

	@Autowired
	private SpecialtyService specService;
	
	@GetMapping
	public List<Specialty> getAllBlogs() {
		return specService.getAllSpecialty();
	}
}
