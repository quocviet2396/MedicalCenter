package com.medicalcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.medicalcenter", "com.medicalcenter.mappers"})
public class MedicalCenterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicalCenterServiceApplication.class, args);
	}

}
