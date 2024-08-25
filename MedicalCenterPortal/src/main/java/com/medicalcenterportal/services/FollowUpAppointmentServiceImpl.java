package com.medicalcenterportal.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.medicalcenter.entities.Appointment;
import com.medicalcenter.entities.AppointmentNoLogin;
import com.medicalcenter.entities.FollowUpAppointment;
import com.medicalcenter.entities.User;
import com.medicalcenter.repositories.UserRepository;
import com.medicalcenterportal.repository.FollowUpAppointmentRepository;

import jakarta.mail.internet.MimeMessage;

@Service
public class FollowUpAppointmentServiceImpl implements FollowUpAppointmentService {
	@Autowired
	private  FollowUpAppointmentRepository followUpAppointmentRepository;
	@Autowired 
	private JavaMailSender eMailSender;
	@Autowired
	private UserRepository userRepository;
	@Override
	public FollowUpAppointment saveOrUpdate(FollowUpAppointment appointment) {
		 return followUpAppointmentRepository.save(appointment);
	}

	@Override
	public Optional<FollowUpAppointment> findById(Long id) {
		// TODO Auto-generated method stub
		return  followUpAppointmentRepository.findById(id);
	}

	@Override
	public List<FollowUpAppointment> findAll() {
		// TODO Auto-generated method stub
		return  followUpAppointmentRepository.findAll();
	}

	@Override
	public void deleteById(Long id) {
		followUpAppointmentRepository.deleteById(id);
		
	}

	@Override
	public FollowUpAppointment updateFlolFollowUpAppointment(Long id, FollowUpAppointment appointment) {
		 Optional<FollowUpAppointment> existingAppointmentOptional = followUpAppointmentRepository.findById(id);
	        if (existingAppointmentOptional.isPresent()) {
	        	FollowUpAppointment existingAppointment = existingAppointmentOptional.get();
	            existingAppointment.setDoctorUser(appointment.getDoctorUser());
	            existingAppointment.setDescription(appointment.getDescription());
	            FollowUpAppointment updatedAppointment = followUpAppointmentRepository.save(existingAppointment);
	            
	            sendDoctorAssignedEmail(updatedAppointment, updatedAppointment.getDoctorUser().getFullName(), null);
	            return updatedAppointment;
	        } else {
	            return null;
	        }
	}
	private void sendDoctorAssignedEmail(FollowUpAppointment appointment, String doctorName, LocalDate appointmentdate) {
	    try {
	        MimeMessage message = eMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        // Sử dụng địa chỉ email từ đối tượng AppointmentNoLogin làm người gửi
	        helper.setFrom("n.luu.gia.bao@gmail.com");
	        helper.setTo(appointment.getPatientUser().getEmail()); 
	        LocalDate appointmentDate = appointment.getAppointmentDate();
	        helper.setSubject("Thông báo: Bạn đã được chỉ định bác sĩ cho lịch tái khám và Ngày tái  của mình.");
	        String text = "Xin chào,\n\nBạn đã được chỉ định bác sĩ " + doctorName + "Ngày" + appointmentDate +" cho cuộc hẹn của mình vui lòng đến đúng giờ để được hướng dẫn.\n\nTrân trọng.";
	        helper.setText(text);
	        eMailSender.send(message);
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Xử lý khi gặp lỗi khi gửi email
	    }
	}

	@Override
	public List<FollowUpAppointment> findByDoctor(User doctor) {
		// TODO Auto-generated method stub
		return followUpAppointmentRepository.findByDoctorUser(doctor);
	}

	@Override
	public List<FollowUpAppointment> findByPatientUser(User patientUser) {
		// TODO Auto-generated method stub
		return followUpAppointmentRepository.findByPatientUser(patientUser);
	}

	@Override
	public List<FollowUpAppointment> getAppointmentsByPatientId(Long patientId) {
		// TODO Auto-generated method stub
		 return followUpAppointmentRepository.findByPatientUser_Id(patientId);
	}
}
