package com.medicalcenter.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
@MappedSuperclass
public class AbstractEntity implements Serializable {

	private static final long serialVersionUID = -4372606085248340992L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	@Column(name = "create_on")
	protected LocalDateTime createOn;
	@Column(name = "update_on")
	protected LocalDateTime updateOn;

	 @PrePersist
	    public void onCreate() {
	        this.createOn = LocalDateTime.now();
	        this.updateOn = this.createOn;
	        
	    }

	    @PreUpdate
	    public void onUpdate() {
	        this.updateOn = LocalDateTime.now();
	    }
}
