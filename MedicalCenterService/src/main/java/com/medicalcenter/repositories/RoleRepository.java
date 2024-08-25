package com.medicalcenter.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.medicalcenter.entities.Role;


@Repository
public interface RoleRepository extends SearchRepository<Role, Long> {
	
	@Query("SELECT r FROM Role r WHERE CONCAT(r.id, ' ', r.name, ' ', r.description) LIKE %?1%")
	public Page<Role> findAll(@Param("searchText") String searchText, Pageable pageable);
	
	Role findByName(String name);
}
