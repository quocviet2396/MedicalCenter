package com.medicalcenterportal.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.Authentication;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.medicalcenter.entities.Appointment;
import com.medicalcenter.entities.AppointmentNoLogin;
import com.medicalcenter.entities.FollowUpAppointment;
import com.medicalcenter.entities.ScheduleItem;
import com.medicalcenter.entities.Specialty;
import com.medicalcenter.entities.User;
import com.medicalcenter.enums.Period;
import com.medicalcenter.exceptions.UserNotFoundException;
import com.medicalcenter.repositories.UserRepository;
import com.medicalcenter.services.UserService;
import com.medicalcenterportal.repository.AppointmentRepository;
import com.medicalcenterportal.repository.ScheduleItemRepository;
import com.medicalcenterportal.services.AppointmentUserLogin;
import com.medicalcenterportal.services.FollowUpAppointmentService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/followupappointments")
public class FollowUpAppointmentController {
	@Autowired 
	FollowUpAppointmentService followUpAppointmentService;
	@Autowired 
	UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired 
	AppointmentUserLogin appointmentUserLogin;
	@Autowired 
	private ScheduleItemRepository  scheduleItemRepository;
	@Autowired
	private AppointmentRepository appointmentRepository;
	@GetMapping("/list")
	public String listFollowUpAppointments(Model model, Principal principal) {
	    // Lấy tên người dùng đang đăng nhập
	    String username = principal.getName();

	    // Kiểm tra vai trò của người dùng đăng nhập
	    boolean isAdmin = false; // Giả sử mặc định là người dùng không phải là ADMIN
	    boolean isDoctor = false; // Giả sử mặc định là người dùng không phải là bác sĩ
	    for (GrantedAuthority authority : ((Authentication) principal).getAuthorities()) {
	        if (authority.getAuthority().equals("ADMIN")) {
	            isAdmin = true;
	            break;
	        } else if (authority.getAuthority().equals("DOCTOR")) {
	            isDoctor = true;
	            break;
	        }
	    }

	    List<FollowUpAppointment> followUpAppointments = new ArrayList<>();
	    LocalDate currentDate = LocalDate.now(); // Lấy ngày hiện tại

	    if (isAdmin) {
	        // Nếu người dùng có vai trò ADMIN, lấy tất cả cuộc hẹn Follow-Up
	        followUpAppointments = followUpAppointmentService.findAll();
	    } else if (isDoctor) {
	        // Nếu người dùng có vai trò là DOCTOR, lấy các cuộc hẹn mà bác sĩ đó tham gia
	        User doctor = userRepository.getUserByEmail(username);
	        List<FollowUpAppointment> doctorAppointments = followUpAppointmentService.findByDoctor(doctor);
	        // Lọc ra các cuộc hẹn có appointmentDate là ngày hiện tại
	        for (FollowUpAppointment appointment : doctorAppointments) {
	            if (appointment.getAppointmentDate().isEqual(currentDate)) {
	                followUpAppointments.add(appointment);
	            }
	        }
	    } else {    
	        // Nếu người dùng có vai trò PATIENT, lấy các cuộc hẹn của người dùng đó
	        User user = userRepository.getUserByEmail(username);
	        followUpAppointments = followUpAppointmentService.findByPatientUser(user);
	    }

	    // Sắp xếp danh sách theo thời gian tạo từ mới nhất đến cũ nhất
	    Collections.sort(followUpAppointments, Comparator.comparing(FollowUpAppointment::getCreateOn).reversed());

	    // Thêm danh sách cuộc hẹn vào model
	    model.addAttribute("followUpAppointments", followUpAppointments);

	    return "/apps/followupAppointment/listFollowUp";
	}


	
	
	@GetMapping("/{id}")
	public String showEditAppointmentForm(@PathVariable("id") Long id, Model model) {
	    // Lấy cuộc hẹn theo ID
	
		Optional<Appointment> appointmentOptional = appointmentUserLogin.getAppointmentById(id);
		
		// Kiểm tra xem cuộc hẹn có tồn tại không
		if (appointmentOptional.isPresent()) {
		    Appointment appointment = appointmentOptional.get();
		   
		    
		
		       // Tiếp tục xử lý với cuộc hẹn đã tìm thấy
		    // Tạo một đối tượng FollowUpAppointment và thiết lập các thuộc tính từ cuộc hẹn ban đầu
		    FollowUpAppointment followUpAppointment = new FollowUpAppointment();
		
		    followUpAppointment.setPatientUser(appointment.getPatientUser());
		    followUpAppointment.setSpecial(appointment.getSpecialties());
		    

		    model.addAttribute("followUpAppointment", followUpAppointment);

		    // Trả về view cho việc chỉnh sửa thông tin cuộc hẹn
		    return "newFollowUpAppointment";
		} else {
		    // Xử lý trường hợp không tìm thấy cuộc hẹn, có thể chuyển hướng đến trang lỗi hoặc trả về thông báo lỗi cụ thể
		    return "redirect:/error"; // Ví dụ chuyển hướng đến trang lỗi
		}
	}
	@GetMapping("/edit/{id}")
	public String showEditFollowUpAppointment(@PathVariable("id") Long id, Model model) {
	    // Lấy thông tin cuộc hẹn từ ID
	    Optional<FollowUpAppointment> followUpAppointmentOptional = followUpAppointmentService.findById(id);
	    
	    // Kiểm tra xem followUpAppointmentOptional có giá trị không trước khi truy cập
	    if (followUpAppointmentOptional.isPresent()) {
	        FollowUpAppointment followUpAppointment = followUpAppointmentOptional.get();
	        
	        model.addAttribute("followUpAppointment", followUpAppointment);

	        // Lấy thông tin về ngày, buổi hẹn và chuyên khoa từ FollowUpAppointment
	        LocalDate appointmentDate = followUpAppointment.getAppointmentDate();
	        Period appointmentPeriod = followUpAppointment.getAppointmentPeriod();
	        Specialty specialty = followUpAppointment.getSpecial();

	        // Lấy danh sách các bác sĩ có lịch làm việc vào ngày, buổi và thuộc chuyên khoa đã chọn
	        List<User> doctors = scheduleItemRepository.findDoctorsByWorkdateAndAppointmentPeriodAndSpecialty(appointmentDate, appointmentPeriod, specialty.getId());
	        model.addAttribute("doctors", doctors);

	        return "editFollowUpAppointment";
	    } else {
	        // Xử lý trường hợp không tìm thấy cuộc hẹn
	        return "redirect:/error"; // Chuyển hướng đến trang lỗi
	    }
	}


	@PostMapping("/update")
	public String updateAppointment(HttpServletRequest request, @ModelAttribute("followUpAppointment") FollowUpAppointment followUpAppointment, Model model) {
	    String idStr = request.getParameter("id");
	    Long appointmentId = Long.valueOf(idStr);
	    
	    // Lấy thông tin cuộc hẹn hiện tại từ cơ sở dữ liệu
	    FollowUpAppointment currentAppointment = followUpAppointmentService.findById(appointmentId)
	            .orElseThrow(() -> new IllegalArgumentException("Invalid appointment id: " + appointmentId));
	    
	    // Nếu trường doctor trong đối tượng followUpAppointment là null,
	    // giữ lại bác sĩ đã được lưu trong cuộc hẹn hiện tại
	    if (followUpAppointment.getDoctorUser() == null) {
	        followUpAppointment.setDoctorUser(currentAppointment.getDoctorUser());
	    }
	    
	    // Cập nhật thông tin cuộc hẹn
	    followUpAppointmentService.updateFlolFollowUpAppointment(appointmentId, followUpAppointment);
	    
	    return "redirect:/followupappointments/list";
	}

	@PostMapping("/save")
	public String saveFollowUpAppointment(@RequestParam("patientUserId") Long patientUserId,
	                                      @RequestParam("specialtyId") Long specialtyId,
	                                      @RequestParam("appointmentDate") LocalDate appointmentDate,
	                                      @RequestParam("description") String description,@RequestParam("appointmentPeriod") Period appointmentPeriod ) throws UserNotFoundException {
	    // Lấy thông tin của bệnh nhân từ cơ sở dữ liệu bằng id
	   User patientUser = userService.get(patientUserId);
	    // Lấy thông tin của chuyên khoa từ cơ sở dữ liệu bằng id
	    Specialty specialty = appointmentRepository.findSpecialtyById(specialtyId);

	    // Kiểm tra xem liệu thông tin của bệnh nhân và chuyên khoa có tồn tại không
	    if (patientUser != null && specialty != null) {
	        // Tạo một đối tượng FollowUpAppointment và thiết lập các thuộc tính từ dữ liệu người dùng
	        FollowUpAppointment followUpAppointment = new FollowUpAppointment();
	        
	        followUpAppointment.setPatientUser(patientUser);
	        followUpAppointment.setSpecial(specialty);
	        followUpAppointment.setAppointmentDate(appointmentDate);
	        followUpAppointment.setAppointmentPeriod(appointmentPeriod);
	        followUpAppointment.setDescription(description);

	        // Lưu hoặc cập nhật cuộc hẹn Follow-Up thông qua service
	        followUpAppointmentService.saveOrUpdate(followUpAppointment);

	        // Chuyển hướng về trang danh sách cuộc hẹn sau khi lưu thành công
	        return "redirect:/followupappointments/list"; // Chuyển hướng đến trang danh sách cuộc hẹn
	    } else {
	        // Xử lý trường hợp không tìm thấy thông tin bệnh nhân hoặc chuyên khoa
	        return "redirect:/error"; // Chuyển hướng đến trang lỗi
	    }
	}

	@GetMapping("/newfollowup/{id}")
	public String showFollowUp(@PathVariable("id") Long id, Model model) {
	    // Lấy thông tin cuộc hẹn từ ID
	    Optional<FollowUpAppointment> followUpAppointmentOptional = followUpAppointmentService.findById(id);
	    
	    // Kiểm tra xem followUpAppointmentOptional có giá trị không trước khi truy cập
	    if (followUpAppointmentOptional.isPresent()) {
	        FollowUpAppointment followUpAppointment = followUpAppointmentOptional.get();

	        // Tạo một đối tượng FollowUpAppointment mới để truyền vào form
	        FollowUpAppointment formFollowUpAppointment = new FollowUpAppointment();
	        formFollowUpAppointment.setPatientUser(followUpAppointment.getPatientUser());
	        formFollowUpAppointment.setSpecial(followUpAppointment.getSpecial());

	        model.addAttribute("followUpAppointment", formFollowUpAppointment);

	        return "newFollowUpAppointment"; // Trả về view để hiển thị form
	    } else {
	        // Xử lý trường hợp không tìm thấy cuộc hẹn
	        return "redirect:/error"; // Chuyển hướng đến trang lỗi
	    }
	}


	
}
