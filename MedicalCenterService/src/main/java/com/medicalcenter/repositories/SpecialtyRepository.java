package com.medicalcenter.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.medicalcenter.entities.Specialty;


@Repository
public interface SpecialtyRepository extends SearchRepository<Specialty, Long> {
	
	@Query("SELECT s FROM Specialty s WHERE CONCAT(s.id, ' ', s.nameSpecialty) LIKE %?1%")
	public Page<Specialty> findAll(@Param("searchText") String searchText, Pageable pageable);
	
	Specialty findByNameSpecialty(String nameSpecialty);
}
