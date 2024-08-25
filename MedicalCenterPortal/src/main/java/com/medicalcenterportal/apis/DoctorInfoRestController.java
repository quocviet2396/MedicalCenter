package com.medicalcenterportal.apis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicalcenter.entities.DoctorInfo;
import com.medicalcenter.services.DoctorInfoService;


@RestController
@RequestMapping({"/apis/v1/doctorinfosgrid", "/apis/test/doctorinfosgrid"})
public class DoctorInfoRestController {

	@Autowired
	private DoctorInfoService doctorInfoService;
	
	@GetMapping
	public List<DoctorInfo> getAllDoctorInfos() {
		return doctorInfoService.getAllDoctorInfo();
	}
}
