package com.medicalcenterportal.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.medicalcenter.entities.AppointmentNoLogin;
import com.medicalcenter.entities.User;

public interface AppointmentNoLoginService {
	
	
	List<AppointmentNoLogin> getAllAppointments();
    Optional<AppointmentNoLogin> getAppointmentById(Long id);
    AppointmentNoLogin saveAppointment(AppointmentNoLogin appointment);
    AppointmentNoLogin updateAppointment(Long id, AppointmentNoLogin appointment);
    void deleteAppointment(Long id);
    List<AppointmentNoLogin> getAppointmentsByDoctor(User doctor, LocalDate currentDate) ;
}
