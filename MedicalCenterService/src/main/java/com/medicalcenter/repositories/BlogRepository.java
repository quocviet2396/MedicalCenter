package com.medicalcenter.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.medicalcenter.entities.Blog;


@Repository
public interface BlogRepository extends SearchRepository<Blog, Long> {

	@Query("SELECT b FROM Blog b WHERE CONCAT(b.id, ' ', b.title, ' ', b.author) LIKE %?1%")
	public Page<Blog> findAll(@Param("searchText") String searchText, Pageable pageable);
	
	Blog findByTitle(String title);
}
