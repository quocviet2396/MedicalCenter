package com.medicalcenterportal.services;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.medicalcenter.entities.Appointment;
import com.medicalcenter.entities.ScheduleItem;
import com.medicalcenter.entities.Specialty;
import com.medicalcenter.entities.User;
import com.medicalcenter.enums.Period;
import com.medicalcenter.repositories.UserRepository;
import com.medicalcenterportal.repository.AppointmentRepository;

import jakarta.mail.internet.MimeMessage;
@Service
public class AppointmentUserLoginImpl implements AppointmentUserLogin {

	
	@Autowired 
	private AppointmentRepository appointmentRepository;
	@Autowired 
	private JavaMailSender eMailSender;
	@Autowired
	private UserRepository userRepository;
	@Override
	public List<Appointment> getAllAppointments() {
		// TODO Auto-generated method stub
		return appointmentRepository.findAll();
	}

	@Override
	public Optional<Appointment> getAppointmentById(Long id) {
		return appointmentRepository.findById(id);
	}

	
	@Override
	public Appointment createAppointment(Long doctorId, LocalDate appointmentDate,
	        Period appointmentPeriod, Long specialtyId,
	        String symptoms) {
	    // Lấy thông tin về người dùng đăng nhập từ Spring Security
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();
	    
	    // Lấy thông tin về patientUser từ username
	    User patientUser = userRepository.findByEmail(username);
	    if (patientUser == null) {
	        throw new IllegalArgumentException("Patient not found");
	    }

	    User doctor = userRepository.findById(doctorId).orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
	    Specialty specialty = null;
	    if (specialtyId != null) {
	        specialty = appointmentRepository.findSpecialtyById(specialtyId);
	        if (specialty == null) {
	            throw new IllegalArgumentException("Specialty not found");
	        }
	    }

	    Appointment appointment = new Appointment();
	    appointment.setPatientUser(patientUser);
	    appointment.setDoctor(doctor);
	    appointment.setAppointmentDate(appointmentDate);
	    appointment.setAppointmentPeriod(appointmentPeriod);
	    appointment.setSpecialties(specialty);
	    appointment.setSymptoms(symptoms);

	    return appointmentRepository.save(appointment);
	}

	@Override
	public Appointment updateAppointment(Long id, Appointment appointment) {
		   Optional<Appointment> existingAppointmentOptional = appointmentRepository.findById(id);
	        if (existingAppointmentOptional.isPresent()) {
	            Appointment existingAppointment = existingAppointmentOptional.get();
	            existingAppointment.setDoctor(appointment.getDoctor());
	            Appointment updatedAppointment = appointmentRepository.save(existingAppointment);
	            
	            sendDoctorAssignedEmail(updatedAppointment, updatedAppointment.getDoctor().getFullName());
	            return updatedAppointment;
	        } else {
	            return null;
	        }
	}
	private void sendDoctorAssignedEmail(Appointment appointment, String doctorName) {
	    try {
	        MimeMessage message = eMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        // Sử dụng địa chỉ email từ đối tượng AppointmentNoLogin làm người gửi
	        helper.setFrom("n.luu.gia.bao@gmail.com");
	        helper.setTo(appointment.getPatientUser().getEmail()); 
	   
	        helper.setSubject("Thông báo: Bạn đã được chỉ định bác sĩ cho cuộc hẹn của mình.");
	        
	        String text = "Xin chào,\n\nBạn đã được chỉ định bác sĩ " + doctorName +" cho cuộc hẹn của mình.\n\nTrân trọng.";
	        helper.setText(text);
	        eMailSender.send(message);
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Xử lý khi gặp lỗi khi gửi email
	    }
	}
	
	@Override
	public void deleteAppointment(Long id) {
		// TODO Auto-generated method stub
		
	}

	public List<Appointment> getAppointmentsByPatientUser(User user) {
        return appointmentRepository.findByPatientUser(user);
    }

	

	@Override
	public List<Appointment> getAppointmentsByDoctor(User doctor, LocalDate currentDate) {
		// TODO Auto-generated method stub
		return appointmentRepository.findByDoctorAndDate(doctor,currentDate);
	}

	@Override
	public List<Appointment> sortAppointmentsByCreateOn(List<Appointment> appointments) {
		  return appointments.stream()
	                .sorted(Comparator.comparing(Appointment::getCreateOn))
	                .collect(Collectors.toList());
	}

	@Override
	public List<Appointment> getAppointmentsByPatientId(Long patientId) {
		// TODO Auto-generated method stub
	return	appointmentRepository.findByPatientUserId(patientId);
	}

}
