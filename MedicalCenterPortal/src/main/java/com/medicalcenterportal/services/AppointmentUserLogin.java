package com.medicalcenterportal.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.medicalcenter.entities.Appointment;
import com.medicalcenter.entities.User;
import com.medicalcenter.enums.Period;

public interface AppointmentUserLogin {
	List<Appointment> getAllAppointments();
    Optional<Appointment> getAppointmentById(Long id);
    Appointment updateAppointment(Long id, Appointment appointment);
    void deleteAppointment(Long id);
	Appointment createAppointment(Long doctorId, LocalDate appointmentDate, Period appointmentPeriod, Long specialtyId,
			String symptoms);

	 List<Appointment> getAppointmentsByPatientUser(User user);
	 List<Appointment> getAppointmentsByDoctor(User doctor, LocalDate currentDate);
	 List<Appointment> sortAppointmentsByCreateOn(List<Appointment> appointments);
	 List<Appointment> getAppointmentsByPatientId(Long patientId);
}
