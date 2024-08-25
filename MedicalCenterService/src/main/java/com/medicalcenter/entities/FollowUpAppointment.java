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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tbFollowUpAppointments")
@Data
@EqualsAndHashCode(callSuper = false)
public class FollowUpAppointment extends AbstractEntity {
	
	private static final long serialVersionUID = -1234567890123456789L;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    private User patientUser;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private User doctorUser;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "specialty_id")
	private Specialty special;
	@Column(name = "appointmentDate")
	private LocalDate appointmentDate;
	@Column(name = "appointment_period")
    @Enumerated(EnumType.STRING)
    private Period appointmentPeriod;
	@Column(length = 256)
	private String description;

	public FollowUpAppointment() {
		
	}

	public FollowUpAppointment(User patientUser, User doctorUser, Specialty specialties, LocalDate appointmentDate,
			Period appointmentPeriod, String description) {
		super();
		this.patientUser = patientUser;
		this.doctorUser = doctorUser;
		this.special = specialties;
		this.appointmentDate = appointmentDate;
		this.appointmentPeriod = appointmentPeriod;
		this.description = description;
	}

	

	
}