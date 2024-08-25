package com.medicalcenterportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.medicalcenterportal", "com.medicalcenter", "com.medicalcenter.mappers"})
@EntityScan({"com.medicalcenter.entities"})
public class MedicalCenterPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicalCenterPortalApplication.class, args);
	}

}
