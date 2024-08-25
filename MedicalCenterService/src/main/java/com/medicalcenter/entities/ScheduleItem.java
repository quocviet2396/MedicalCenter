package com.medicalcenter.entities;

import java.time.LocalDate;


import com.medicalcenter.enums.Period;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tbScheduleItem")
@Data
@EqualsAndHashCode(callSuper = false)
public class ScheduleItem extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name = "doctor_id" ,referencedColumnName = "id")
	private User doctor;
	
	
    @Column(name = "workdate")
    private LocalDate workdates;
	
	private String room;
	@Column(name = "appointment_period", nullable = false)
	@Enumerated(EnumType.STRING)
	private Period appointmentPeriod;
	private String count;
	public ScheduleItem() {
		
	}
	public ScheduleItem(User doctor, LocalDate workdates, String room, Period appointmentPeriod) {
		super();
		this.doctor = doctor;
		this.workdates = workdates;
		this.room = room;
		this.appointmentPeriod = appointmentPeriod;
	}
	
	
}
