package com.medicalcenter.entities;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tbSpecialties")
@Data
@EqualsAndHashCode(callSuper = false)
public class Specialty extends AbstractEntity {

	private static final long serialVersionUID = -7051580332393619050L;

	@Column(length = 64, unique = true, nullable = false)
	private String nameSpecialty;
	
	@Column(nullable = false)
	@NotNull
	private String description;
	
	@Column(length = 1024)
	private String photo;
	// add by bao
			@OneToMany(mappedBy = "specialty")
			private Set<AppointmentNoLogin> appointmentnologins;
			@OneToMany(mappedBy = "specialties")
			private Set<Appointment> appointments;

			@OneToMany(mappedBy = "special")
			private Set<FollowUpAppointment> followUpAppointments ;
			@OneToMany(mappedBy = "specials")
			private Set<FollowUpNoLogin> followUpNoLogins ;
	public Specialty() {
		
	}
	public Specialty(String nameSpecialty, @NotNull String description, String photo) {
		super();
		this.nameSpecialty = nameSpecialty;
		this.description = description;
		this.photo = photo;
	}
}
