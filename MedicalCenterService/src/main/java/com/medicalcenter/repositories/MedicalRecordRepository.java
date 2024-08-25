package com.medicalcenter.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.medicalcenter.entities.MedicalRecord;
import com.medicalcenter.entities.User;

@Repository
public interface MedicalRecordRepository extends SearchRepository<MedicalRecord, Long> {
	
	List<MedicalRecord> findByPatientUserId(Long patientId);

	boolean existsByPatientUserAndDoctorUserAndLastVisitDateAndFollowUpDate(User patientUser, User doctorUser, LocalDateTime lastVisitDate, LocalDateTime followUpDate);


	@Query("SELECT u FROM MedicalRecord u WHERE " +
		       "LOWER(CONCAT(u.patientUser.firstName, ' ', u.patientUser.lastName)) LIKE %:searchText% OR " +
		       "LOWER(CONCAT(u.doctorUser.firstName, ' ', u.doctorUser.lastName)) LIKE %:searchText% OR " +
		       "LOWER(u.description) LIKE %:searchText%")
		Page<MedicalRecord> findAll(@Param("searchText") String searchText, Pageable pageable);

		@Query("SELECT m FROM MedicalRecord m WHERE " +
		       "LOWER(CONCAT(m.patientUser.firstName, ' ', m.patientUser.lastName)) LIKE %:searchText% OR " +
		       "LOWER(CONCAT(m.doctorUser.firstName, ' ', m.doctorUser.lastName)) LIKE %:searchText% OR " +
		       "LOWER(m.description) LIKE %:searchText%")
		List<MedicalRecord> searchMedicalRecordsByKeyword(@Param("searchText") String searchText);

	
	MedicalRecord findById(long id);
	
	@Query("SELECT m FROM MedicalRecord m WHERE m.doctorUser.id = :doctorId")
	List<MedicalRecord> getAllMedicalRecordsByDoctorId(@Param("doctorId") Long doctorId);

	 List<MedicalRecord> findByDoctorUserIdAndLastVisitDateBetween(Long doctorId, LocalDateTime startDate, LocalDateTime endDate);	 
	 @SuppressWarnings("unchecked")
	    MedicalRecord save(MedicalRecord medicalrecord);
	    List<MedicalRecord> findByLastVisitDateBetween(LocalDateTime startDate, LocalDateTime endDate);

	
}
