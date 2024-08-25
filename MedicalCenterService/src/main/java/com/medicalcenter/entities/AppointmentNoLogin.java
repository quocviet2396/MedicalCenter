package com.medicalcenter.entities;

import java.time.LocalDate;

import com.medicalcenter.enums.Gender;
import com.medicalcenter.enums.Period;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity
@Table(name = "tbAppointmentNoLogin")
@Data
@EqualsAndHashCode(callSuper = false)
public class AppointmentNoLogin  extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	 
	 	
		@Column(name = "order_number")
		private Integer orderNumber;

		@Column(name = "full_name", nullable = false)
	    private String fullName;

	    @Column(name = "email", nullable = false)
	    private String email;

	    @Column(name = "birthday", nullable = false)
	    private LocalDate birthday;

	    @Column(name = "phone_number", nullable = false)
	    private String phoneNumber;
	    
	    @Column(name = "gender")
	    @Enumerated(EnumType.STRING)
		private Gender gender;
	    @Column(name = "arrived")
	    private String arrived;
	    @Column(name = "address", nullable = false)
	    private String address;

	    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	    @JoinColumn(name = "specialty_id", nullable = false)
	    private Specialty specialty;

	    @Column(name = "appointment_date", nullable = false)
	    private LocalDate appointmentDate;
	    @Column(name = "appointment_period", nullable = false)
	    @Enumerated(EnumType.STRING)
	    private Period appointmentPeriod;
	    
	    @Column(name = "symptoms", nullable = false)
	    private String symptoms;
	   
	    
	    @ManyToOne
	    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
	    private User doctor;
	

	   public AppointmentNoLogin() {
		  
		   
	   }
	   public AppointmentNoLogin(EntityManager entityManager, int orderNumber, String fullName, String email,
					LocalDate birthday, String phoneNumber, Gender gender, String address, Specialty specialty,
					LocalDate appointmentDate, Period appointmentPeriod, String symptoms, User doctor) {
				super();
				
				this.orderNumber = orderNumber;
				this.fullName = fullName;
				this.email = email;
				this.birthday = birthday;
				this.phoneNumber = phoneNumber;
				this.gender = gender;
				this.address = address;
				this.specialty = specialty;
				this.appointmentDate = appointmentDate;
				this.appointmentPeriod = appointmentPeriod;
				this.symptoms = symptoms;
				this.doctor = doctor;
	   }
}
