package com.medicalcenterportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicalcenter.entities.FollowUpAppointment;
import com.medicalcenter.entities.User;

public interface FollowUpAppointmentRepository  extends JpaRepository<FollowUpAppointment, Long>{
	
	List<FollowUpAppointment> findByDoctorUser(User doctor);
	 List<FollowUpAppointment> findByPatientUser(User patientUser);
	 List<FollowUpAppointment> findByPatientUser_Id(Long patientId);
}
