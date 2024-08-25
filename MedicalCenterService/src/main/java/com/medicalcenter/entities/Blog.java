package com.medicalcenter.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tbBlogs")
@Data
@EqualsAndHashCode(callSuper = false)
public class Blog extends AbstractEntity {

	private static final long serialVersionUID = 6575148658066925514L;
	
	@Column(length = 256, nullable = false, unique = true)
	@NotNull
	private String title;
	
	@Column(length = 15000, nullable = false)
	@NotNull
	private String content;
	
	@Column(length = 1024)
	private String photo;
	
	@Column(length = 64)
	private String author;
	
	//@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name = "blog_id")
    //private List<Comment> comments = new ArrayList<>();
	
	@Transient
	public String getPhotosImagePath() {
		if (id == null || photo == null)
			return "/images/default-user.png";
		return "/blog-photos/" + this.id + "/" + photo;
	}
	
	public Blog() { 
	
	}
}
