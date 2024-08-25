package com.medicalcenterportal.services;

import java.util.List;
import java.util.Optional;

import com.medicalcenter.entities.AppointmentNoLogin;
import com.medicalcenter.entities.FollowUpAppointment;
import com.medicalcenter.entities.User;

public interface FollowUpAppointmentService {
	FollowUpAppointment saveOrUpdate(FollowUpAppointment appointment);
	 Optional<FollowUpAppointment> findById(Long id);
	
	 List<FollowUpAppointment> findAll();
	 void deleteById(Long id);
	 FollowUpAppointment updateFlolFollowUpAppointment(Long id, FollowUpAppointment appointment);
	 List<FollowUpAppointment> findByDoctor(User doctor);
	 List<FollowUpAppointment> findByPatientUser(User patientUser);
	 List<FollowUpAppointment> getAppointmentsByPatientId(Long patientId);
}
