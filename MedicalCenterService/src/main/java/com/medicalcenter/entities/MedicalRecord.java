package com.medicalcenter.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tbMedicalRecords")
@Data
@EqualsAndHashCode(callSuper = false)
public class MedicalRecord extends AbstractEntity {

	private static final long serialVersionUID = 3947729175042528542L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "patient_id", nullable = false)
	private User patientUser;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "doctor_id", nullable = false)
	private User doctorUser;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private LocalDateTime lastVisitDate;

	@Column(nullable = false)
	private LocalDateTime followUpDate;

	 @Transient
	 private String patientUserPhoneNumber;
	
	public MedicalRecord() {

	}

	public MedicalRecord(User patientUser, User doctorUser, String description,
			LocalDateTime lastVisitDate, LocalDateTime followUpDate) {
		super();
		this.patientUser = patientUser;
		this.doctorUser = doctorUser;
		this.description = description;
		this.lastVisitDate = lastVisitDate;
		this.followUpDate = followUpDate;
		
	}

}