package com.medicalcenter.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tbProducts")
@Data
@EqualsAndHashCode(callSuper = false)
public class Product extends AbstractEntity {

	private static final long serialVersionUID = -394236015316781362L;

	@Column(length = 40, nullable = false)
	private String nameProduct;
	
	@Column(length = 128, nullable = false)
	private String description;
	
	@Column(nullable = false)
	private int quantity;
	
	@Column(nullable = false)
	private double price;
	
	@Column(length = 40, nullable = false)
	private String countryOfOrigin;
	
	@Column(length = 40, nullable = false)
	private String brand;
	
	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;
	
	public Product() {
		
	}

	public Product(String nameProduct, String description, int quantity, double price, String countryOfOrigin,
			String brand, Category category) {
		super();
		this.nameProduct = nameProduct;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
		this.countryOfOrigin = countryOfOrigin;
		this.brand = brand;
		this.category = category;
	}
	
	
	
}