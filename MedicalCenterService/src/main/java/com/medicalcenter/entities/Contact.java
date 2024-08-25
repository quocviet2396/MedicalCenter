package com.medicalcenter.entities;



import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tbContact")
@Data
@EqualsAndHashCode(callSuper = false)
public class Contact extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;
	 private String name;
	    private String title;
	    private String email;
	    private String phone;
	    private String description;
	    private boolean emailSent;
		
		public Contact() {
			// TODO Auto-generated constructor stub
		}
		public Contact(String name, String title, String email, String phone, String description, boolean emailSent) {
			super();
			this.name = name;
			this.title = title;
			this.email = email;
			this.phone = phone;
			this.description = description;
			this.emailSent = emailSent;
		}
	    
	
}
