package com.medicalcenter.dto;

import com.medicalcenter.enums.Gender;

public class UserDTO {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private Gender gender;
    private String photo;
    private boolean enabled;


    // Constructors, getters, and setters

    public UserDTO() {
    }
    
    

    public UserDTO(String email, String firstName, String lastName, boolean enabled) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.enabled = enabled;
	}



	public UserDTO(String email, String password, String firstName, String lastName, String dateOfBirth, Gender gender, String photo, boolean enabled) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.photo = photo;
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}