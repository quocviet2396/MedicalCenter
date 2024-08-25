package com.medicalcenterportal.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.medicalcenter.entities.FollowUpNoLogin;
import com.medicalcenter.entities.User;
import com.medicalcenterportal.repository.FollowUpNoLoginRepository;

import jakarta.mail.internet.MimeMessage;
@Service
public class FollowUpNoLoginServiceImpl implements FollowUpNologinService {

	
	@Autowired 
	FollowUpNoLoginRepository followUpNoLoginRepository;
	@Autowired 
	private JavaMailSender eMailSender;
	@Override
	public List<FollowUpNoLogin> findAll() {
		// TODO Auto-generated method stub
		return followUpNoLoginRepository.findAll();
	}

	@Override
	public FollowUpNoLogin updateFollowUpNoLogin(Long id, FollowUpNoLogin followUpNoLogin) {
	    // Tìm kiếm cuộc hẹn có id tương ứng trong cơ sở dữ liệu
	    Optional<FollowUpNoLogin> existingAppointmentOptional = followUpNoLoginRepository.findById(id);
	    
	    // Kiểm tra xem cuộc hẹn có tồn tại không
	    if (existingAppointmentOptional.isPresent()) {
	        // Nếu cuộc hẹn tồn tại, lấy ra đối tượng cuộc hẹn từ Optional
	        FollowUpNoLogin existingAppointment = existingAppointmentOptional.get();
	        
	        // Kiểm tra nếu trường doctorUser trong đối tượng followUpNoLogin là null
	        // thì giữ nguyên bác sĩ đã được lưu trong cuộc hẹn hiện tại
	        if (followUpNoLogin.getDoctorUser() != null) {
	            existingAppointment.setDoctorUser(followUpNoLogin.getDoctorUser());
	        }
	        
	        // Cập nhật thông tin của cuộc hẹn từ đối tượng followUpNoLogin truyền vào
	        existingAppointment.setDescription(followUpNoLogin.getDescription());
	        
	        // Lưu cuộc hẹn đã được cập nhật vào cơ sở dữ liệu
	        FollowUpNoLogin updatedAppointment = followUpNoLoginRepository.save(existingAppointment);
	        
	        // Gửi email thông báo cho bác sĩ được chỉ định
	        if (updatedAppointment.getDoctorUser() != null) {
	            sendDoctorAssignedEmail(updatedAppointment, updatedAppointment.getDoctorUser().getFullName(), null);
	        }
	        
	        // Trả về cuộc hẹn đã được cập nhật
	        return updatedAppointment;
	    } else {
	        // Nếu cuộc hẹn không tồn tại, trả về null
	        return null;
	    }
	}

	private void sendDoctorAssignedEmail(FollowUpNoLogin appointment, String doctorName, LocalDate appointmentdate) {
	    try {
	        MimeMessage message = eMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        // Sử dụng địa chỉ email từ đối tượng AppointmentNoLogin làm người gửi
	        helper.setFrom("n.luu.gia.bao@gmail.com");
	        helper.setTo(appointment.getPatientUser().getEmail()); 
	        LocalDate appointmentDate = appointment.getAppointmentDate();
	        helper.setSubject("Thông báo: Bạn đã được chỉ định bác sĩ cho lịch tái khám và Ngày tái  của mình.");
	        String text = "Xin chào,\n\nBạn đã được chỉ định bác sĩ " + doctorName + " Ngày  " + appointmentDate +" cho cuộc hẹn của mình vui lòng đến đúng giờ để được hướng dẫn.\n\nTrân trọng.";
	        helper.setText(text);
	        eMailSender.send(message);
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Xử lý khi gặp lỗi khi gửi email
	    }
	}

	@Override
	public Optional<FollowUpNoLogin> findById(Long id) {
		// TODO Auto-generated method stub
		return  followUpNoLoginRepository.findById(id);
	}

	@Override
	public List<FollowUpNoLogin> findByDoctor(User doctor) {
		// TODO Auto-generated method stub
		return followUpNoLoginRepository.findByDoctorUser(doctor);
	}

}
