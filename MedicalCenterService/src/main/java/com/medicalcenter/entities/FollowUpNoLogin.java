package com.medicalcenter.entities;

import java.time.LocalDate;

import com.medicalcenter.enums.Period;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tbFollowUpNoLogin")
@Data
@EqualsAndHashCode(callSuper = false)
public class FollowUpNoLogin extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private AppointmentNoLogin patientUser;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private User doctorUser;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "specialty_id")
	private Specialty specials;
	
	 @Column(name = "appointment_date", nullable = false)
	    private LocalDate appointmentDate;
	    @Column(name = "appointment_period", nullable = false)
	    @Enumerated(EnumType.STRING)
	    private Period appointmentPeriod;
	    @Column(length = 256)
		private String description;

		public FollowUpNoLogin() {
			super();
		}

		public FollowUpNoLogin(AppointmentNoLogin patientUser, LocalDate appointmentDate, Period appointmentPeriod,
				String description) {
			super();
			this.patientUser = patientUser;
			this.appointmentDate = appointmentDate;
			this.appointmentPeriod = appointmentPeriod;
			this.description = description;
		}
	    
	    
}
