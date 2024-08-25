package com.medicalcenter.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicalcenter.entities.MedicineRecord;

public interface MedicinesRecordRepository  extends JpaRepository<MedicineRecord,Long> {
	 List<MedicineRecord> findByMedicalRecordPatientUserId(Long userId);
	 
	 List<MedicineRecord> findByMedicalRecordDoctorUserId(Long userId);
	 
	 List<MedicineRecord> findByMedicalRecord_Id(Long medicalRecordId);
}

