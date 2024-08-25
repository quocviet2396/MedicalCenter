package com.medicalcenter.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tbDoctorInfo")
@Data
@EqualsAndHashCode(callSuper = false)
public class DoctorInfo extends AbstractEntity {
	
	private static final long serialVersionUID = 7618301928723911182L;

	@Column(length = 256, nullable = false, unique = true)
	@NotNull
	private String title;
	
	@Column(length = 15000, nullable = false)
	@NotNull
	private String content;
	
	@Column(length = 1024)
	private String photo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "specialty_id", nullable = true)
	private Specialty specialty;
	
	@Transient
	public String getPhotosImagePath() {
		if (id == null || photo == null)
			return "/images/default-user.png";
		return "/doctorinfo-photos/" + this.id + "/" + photo;
	}
	
	public DoctorInfo() {
		
	}
}
