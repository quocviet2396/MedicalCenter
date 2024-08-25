package com.medicalcenterportal.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.medicalcenter.entities.AppointmentNoLogin;
import com.medicalcenter.entities.Specialty;
import com.medicalcenter.entities.User;
import com.medicalcenterportal.repository.AppointmentNoLoginRepository;

import jakarta.mail.internet.MimeMessage;
@Service
public class AppointmentNoLoginServiceImpl  implements AppointmentNoLoginService{

	@Autowired
    private AppointmentNoLoginRepository appointmentRepository;
	@Autowired
    private JavaMailSender emailSender; 
    @Override
    public List<AppointmentNoLogin> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Optional<AppointmentNoLogin> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public AppointmentNoLogin saveAppointment(AppointmentNoLogin appointment) {
        Specialty specialty = appointment.getSpecialty();
        if (specialty != null && specialty.getId() != null) {
            // Lấy đối tượng specialty từ cơ sở dữ liệu
            Specialty existingSpecialty = appointmentRepository.findSpecialtyById(specialty.getId());
            // Kiểm tra xem đối tượng specialty đã tồn tại hay không
           
        }
        return appointmentRepository.save(appointment);
    }


    @Override
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public AppointmentNoLogin updateAppointment(Long id, AppointmentNoLogin appointment) {
        Optional<AppointmentNoLogin> existingAppointmentOptional = appointmentRepository.findById(id);
        if (existingAppointmentOptional.isPresent()) {
            AppointmentNoLogin existingAppointment = existingAppointmentOptional.get();
            existingAppointment.setDoctor(appointment.getDoctor());
            AppointmentNoLogin updatedAppointment = appointmentRepository.save(existingAppointment);
            sendDoctorAssignedEmail(updatedAppointment, updatedAppointment.getDoctor().getFullName());
            return updatedAppointment;
        } else {
            return null;
        }
    }
	private void sendDoctorAssignedEmail(AppointmentNoLogin appointment, String doctorName) {
	    try {
	        MimeMessage message = emailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        // Sử dụng địa chỉ email từ đối tượng AppointmentNoLogin làm người gửi
	        helper.setFrom("n.luu.gia.bao@gmail.com");
	        helper.setTo(appointment.getEmail()); // Lấy địa chỉ email từ đối tượng AppointmentNoLogin
	        helper.setSubject("Thông báo: Bạn đã được chỉ định bác sĩ cho cuộc hẹn của mình.");
	        String text = "Xin chào,\n\nBạn đã được chỉ định bác sĩ " + doctorName + " cho cuộc hẹn của mình.\n\nTrân trọng.";
	        helper.setText(text);
	        emailSender.send(message);
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Xử lý khi gặp lỗi khi gửi email
	    }
	}

	@Override
	public List<AppointmentNoLogin> getAppointmentsByDoctor(User doctor, LocalDate currentDate) {
		// TODO Auto-generated method stub
		return appointmentRepository.findByDoctorAndAppointmentDate(doctor, currentDate);
	}


}
