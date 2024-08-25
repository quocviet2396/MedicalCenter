package com.medicalcenterportal.services;

import com.medicalcenter.enums.Period;
import com.medicalcenter.repositories.UserRepository;
import com.medicalcenter.entities.Appointment;
import com.medicalcenter.entities.AppointmentNoLogin;
import com.medicalcenter.entities.CombinedAppointment;
import com.medicalcenterportal.repository.AppointmentNoLoginRepository;
import com.medicalcenterportal.repository.AppointmentRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentListOfDoctorService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentNoLoginRepository appointmentNoLoginRepository;
    public List<CombinedAppointment> getAppointmentsByDoctor(Long doctorId) {
        List<CombinedAppointment> combinedAppointments = new ArrayList<>();

        // Lấy ngày hiện tại
        LocalDate currentDate = LocalDate.now();

        // Lấy danh sách các cuộc hẹn từ bảng tbAppointments và chuyển đổi thành CombinedAppointment
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);
        for (Appointment appointment : appointments) {
            if (appointment.getDoctor() != null && appointment.getAppointmentDate().equals(currentDate)) { 
                // Kiểm tra xem doctor có null không và ngày hẹn phải là ngày hiện tại
                CombinedAppointment combinedAppointment = new CombinedAppointment();
                combinedAppointment.setId(appointment.getId());
                combinedAppointment.setCreateOn(appointment.getCreateOn());
                combinedAppointment.setFullName(appointment.getPatientUser().getFullName());
                combinedAppointment.setBirthday (appointment.getPatientUser().getDateOfBirth());
                combinedAppointment.setEmail(appointment.getPatientUser().getEmail());
                combinedAppointment.setDoctorName(appointment.getDoctor().getFullName());
                combinedAppointment.setAppointmentDate(appointment.getAppointmentDate());
                combinedAppointment.setAppointmentPeriod(appointment.getAppointmentPeriod());
                combinedAppointment.setSpecialtyName(appointment.getSpecialties().getNameSpecialty());
                combinedAppointment.setSymptoms(appointment.getSymptoms());
                combinedAppointment.setArrived(appointment.getArrived());
                combinedAppointments.add(combinedAppointment);
            }
        }

        // Lấy danh sách các cuộc hẹn từ bảng tbAppointmentNoLogin và chuyển đổi thành CombinedAppointment
        List<AppointmentNoLogin> appointmentsNoLogin = appointmentNoLoginRepository.findByDoctorId(doctorId);
        for (AppointmentNoLogin appointmentNoLogin : appointmentsNoLogin) {
            if (appointmentNoLogin.getDoctor() != null && appointmentNoLogin.getAppointmentDate().equals(currentDate)) {
                // Kiểm tra xem doctor có null không và ngày hẹn phải là ngày hiện tại
                CombinedAppointment combinedAppointment = new CombinedAppointment();
                combinedAppointment.setId(appointmentNoLogin.getId());
                combinedAppointment.setCreateOn(appointmentNoLogin.getCreateOn());
                combinedAppointment.setFullName(appointmentNoLogin.getFullName());
                combinedAppointment.setBirthday(appointmentNoLogin.getBirthday().toString());

                combinedAppointment.setEmail(appointmentNoLogin.getEmail());
                combinedAppointment.setDoctorName(appointmentNoLogin.getDoctor().getFullName());
                combinedAppointment.setAppointmentDate(appointmentNoLogin.getAppointmentDate());
                combinedAppointment.setAppointmentPeriod(appointmentNoLogin.getAppointmentPeriod());
                combinedAppointment.setSpecialtyName(appointmentNoLogin.getSpecialty().getNameSpecialty());
                combinedAppointment.setSymptoms(appointmentNoLogin.getSymptoms());
                combinedAppointment.setArrived(appointmentNoLogin.getArrived());
                combinedAppointments.add(combinedAppointment); 	
            }
        }
        
        // Sắp xếp danh sách cuộc hẹn theo yêu cầu
        combinedAppointments.sort(Comparator.comparing((CombinedAppointment appointment) ->
                appointment.getAppointmentPeriod() == Period.Morning ? 0 : 1)
                .thenComparing(CombinedAppointment::getCreateOn));
        
        return combinedAppointments;
    }

    public List<CombinedAppointment> getAllAppointments() {
        List<CombinedAppointment> combinedAppointments = new ArrayList<>();

        // Lấy ngày hiện tại
        LocalDate currentDate = LocalDate.now();

        // Lấy danh sách các cuộc hẹn từ bảng tbAppointments và chuyển đổi thành CombinedAppointment
        List<Appointment> appointments = appointmentRepository.findByAppointmentDate(currentDate);
        for (Appointment appointment : appointments) {
            if (appointment.getDoctor() != null) { // Kiểm tra xem doctor có null không
                CombinedAppointment combinedAppointment = new CombinedAppointment();
                combinedAppointment.setId(appointment.getId());
                combinedAppointment.setCreateOn(appointment.getCreateOn());
                combinedAppointment.setFullName(appointment.getPatientUser().getFullName());
                combinedAppointment.setDoctorName(appointment.getDoctor().getFullName());
                combinedAppointment.setAppointmentDate(appointment.getAppointmentDate());
                combinedAppointment.setAppointmentPeriod(appointment.getAppointmentPeriod());
                combinedAppointment.setSpecialtyName(appointment.getSpecialties().getNameSpecialty());
                combinedAppointment.setSymptoms(appointment.getSymptoms());
               
                combinedAppointments.add(combinedAppointment);
            }
        }

        // Lấy danh sách các cuộc hẹn từ bảng tbAppointmentNoLogin và chuyển đổi thành CombinedAppointment
        List<AppointmentNoLogin> appointmentsNoLogin = appointmentNoLoginRepository.findByAppointmentDate(currentDate);
        for (AppointmentNoLogin appointmentNoLogin : appointmentsNoLogin) {
            if (appointmentNoLogin.getDoctor() != null) { // Kiểm tra xem doctor có null không
                CombinedAppointment combinedAppointment = new CombinedAppointment();
                combinedAppointment.setId(appointmentNoLogin.getId());
                combinedAppointment.setCreateOn(appointmentNoLogin.getCreateOn());
                combinedAppointment.setFullName(appointmentNoLogin.getFullName());
                combinedAppointment.setDoctorName(appointmentNoLogin.getDoctor().getFullName());
                combinedAppointment.setAppointmentDate(appointmentNoLogin.getAppointmentDate());
                combinedAppointment.setAppointmentPeriod(appointmentNoLogin.getAppointmentPeriod());
                combinedAppointment.setSpecialtyName(appointmentNoLogin.getSpecialty().getNameSpecialty());
                combinedAppointment.setSymptoms(appointmentNoLogin.getSymptoms());
               
                combinedAppointments.add(combinedAppointment); 	
            }
        }
        
        // Sắp xếp danh sách cuộc hẹn theo yêu cầu
        combinedAppointments.sort(Comparator.comparing((CombinedAppointment appointment) ->
                appointment.getAppointmentPeriod() == Period.Morning ? 0 : 1)
                .thenComparing(CombinedAppointment::getCreateOn));
        
        return combinedAppointments;
    }
    public void updateArrivedStatus(Long appointmentId, String arrived) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
        if (optionalAppointment.isPresent()) {
        	 CombinedAppointment combinedAppointment = new CombinedAppointment();
            Appointment appointment = optionalAppointment.get();
            appointment.setArrived(arrived);
            appointmentRepository.save(appointment);
        } 
            Optional<AppointmentNoLogin> optionalAppointmentNoLogin = appointmentNoLoginRepository.findById(appointmentId);
            if (optionalAppointmentNoLogin.isPresent()) {
                AppointmentNoLogin appointmentNoLogin = optionalAppointmentNoLogin.get();
                appointmentNoLogin.setArrived(arrived);
                appointmentNoLoginRepository.save(appointmentNoLogin);
            } 
               
            
        }
    }

  

