package com.medicalcenterportal.apis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicalcenter.entities.Blog;
import com.medicalcenter.services.BlogService;


@RestController
@RequestMapping({"/apis/v1/blogsgrid", "/apis/test/blogsgrid"})
public class BlogRestController {

	@Autowired
	private BlogService blogService;
	
	@GetMapping
	public List<Blog> getAllBlogs() {
		return blogService.getAllBlog();
	}
	
}
