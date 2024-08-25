package com.medicalcenter.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.medicalcenter.entities.DoctorInfo;

@Repository
public interface DoctorInfoRepository extends SearchRepository<DoctorInfo, Long> {

	@Query("SELECT d FROM DoctorInfo d WHERE CONCAT(d.id, ' ', d.title) LIKE %?1%")
	public Page<DoctorInfo> findAll(@Param("searchText") String searchText, Pageable pageable);

	DoctorInfo findByTitle(String title);

	List<DoctorInfo> findBySpecialtyId(Long specialtyId);
}