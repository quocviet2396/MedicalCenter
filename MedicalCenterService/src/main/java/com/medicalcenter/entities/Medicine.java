package com.medicalcenter.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tbMedicines")
@Data
@EqualsAndHashCode(callSuper = false)
public class Medicine {

	@Id
	private Long id;
	
	private int _version;
	
	@Column(name = "genericName")
	private String genericName;
	
	@Column(name = "brandName")
	private String brandName;
	
	@Column(name = "measurementUnit")
	private String measurementUnit;
	
	private String form;
	
	public Medicine() {
		
	}

	public Medicine(Long id, int _version, String genericName, String brandName, String measurementUnit,
			String form) {
		super();
		this.id = id;
		this._version = _version;
		this.genericName = genericName;
		this.brandName = brandName;
		this.measurementUnit = measurementUnit;
		this.form = form;
	}

	
}