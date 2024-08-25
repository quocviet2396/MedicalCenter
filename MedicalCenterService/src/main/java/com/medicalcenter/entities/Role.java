package com.medicalcenter.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "tbRole")
@EqualsAndHashCode(callSuper = false)
public class Role extends AbstractEntity {

	private static final long serialVersionUID = 364756099059685022L;

	@Column(length = 40, unique = true, nullable = false)
	private String name;
	@Column(length = 128, nullable = false)
	private String description;
}
