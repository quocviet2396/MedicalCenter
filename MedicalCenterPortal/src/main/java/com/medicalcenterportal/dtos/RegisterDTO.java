package com.medicalcenterportal.dtos;

import jakarta.validation.constraints.NotBlank;

public class RegisterDTO {
	@NotBlank
	private String email;

	@NotBlank
	private String password;

	private String lastName;
	
	private String firstName;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
