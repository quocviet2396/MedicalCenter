package com.medicalcenter.entities;

import java.time.LocalDate;



import com.medicalcenter.enums.Period;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tbAppointments")
@Data
@EqualsAndHashCode(callSuper = false)
public class Appointment extends AbstractEntity{
	
	private static final long serialVersionUID = -5421813867180785414L;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private User patientUser;
	
	 @ManyToOne
	    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
	    private User doctor;
	 @Column(name = "appointment_date", nullable = false)
	    private LocalDate appointmentDate;
	    @Column(name = "appointment_period", nullable = false)
	    @Enumerated(EnumType.STRING)
	    private Period appointmentPeriod;
	    

	    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	    @JoinColumn(name = "specialty_id", nullable = false)
	    private Specialty specialties;

	    @Column(name = "arrived")
	    private String arrived;

	
	

	@Column(length = 256, nullable = false)
	private String symptoms;
	
	public Appointment() {
	
		
	}

	
	 public void setPatientUser(User patientUser) {
	        this.patientUser = patientUser;
	    }


	public Appointment(User patientUser, User doctor, LocalDate appointmentDate, Period appointmentPeriod,
			Specialty specialties, String symptoms) {
		super();
		this.patientUser = patientUser;
		this.doctor = doctor;
		this.appointmentDate = appointmentDate;
		this.appointmentPeriod = appointmentPeriod;
		this.specialties = specialties;
		this.symptoms = symptoms;
	}


	

	

	




	
	

}
