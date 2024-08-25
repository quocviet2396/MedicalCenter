package com.medicalcenter.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicalcenter.entities.Blog;
import com.medicalcenter.paging.PagingAndSortingHelper;
import com.medicalcenter.repositories.BlogRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BlogService {

	@Autowired
	private BlogRepository blogRepo;
	
	public void listBlogByPage(int pageNum, int pageCount, PagingAndSortingHelper helper) {
		helper.listEntities(pageNum, pageCount, blogRepo);
	}
	
	public Blog createBlog(Blog blog) {
		blog.setCreateOn(LocalDateTime.now());
		return blogRepo.save(blog);
	}
	
	public List<Blog> getAllBlog() {
		
		return (List<Blog>) blogRepo.findAll();
	}
	
	public Blog getBlogById(Long id) {
        return blogRepo.findById(id).orElse(null);
    }

    public Blog updateBlog(Blog blogDto) {
    	Blog blogInDB = blogRepo.findById(blogDto.getId()).get();
    	blogInDB.setTitle(blogDto.getTitle());
    	blogInDB.setContent(blogDto.getContent());
    	blogInDB.setAuthor(blogDto.getAuthor());
    	blogInDB.setUpdateOn(LocalDateTime.now());

    	if (blogDto.getPhoto() != null) {
	        blogInDB.setPhoto(blogDto.getPhoto());
	    }
        return blogRepo.save(blogInDB);
    }

    public void deleteBlog(Long id) {
    	blogRepo.deleteById(id);
    }

    public Blog findByTitle(String title) {
        return blogRepo.findByTitle(title);
    }
}
