package com.medicalcenter.entities;

import com.medicalcenter.entities.Category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tbCategories")
@Data
@EqualsAndHashCode(callSuper = false)
public class Category extends AbstractEntity {
	
	private static final long serialVersionUID = 1587930292109755256L;
	
	@Column(length = 40, unique = true, nullable = false)
	private String nameCategory;
	@Column(length = 128, nullable = false)
	private String description;
}
