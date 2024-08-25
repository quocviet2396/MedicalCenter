package com.medicalcenterportal.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medicalcenter.entities.AppointmentNoLogin;
import com.medicalcenter.entities.Specialty;
import com.medicalcenter.entities.User;
import com.medicalcenter.enums.Period;
import com.medicalcenter.repositories.UserRepository;
import com.medicalcenterportal.repository.AppointmentNoLoginRepository;
import com.medicalcenterportal.repository.ScheduleItemRepository;
import com.medicalcenterportal.services.AppointmentNoLoginService;
import com.medicalcenterportal.services.EmailService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/appointments")
public class AppointmentNoLoginController {

    @Autowired
    private AppointmentNoLoginService appointmentService;
    @Autowired
    private AppointmentNoLoginRepository appointmentRepository; 
	@Autowired 
	private ScheduleItemRepository  scheduleItemRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired 
	private EmailService emailService;
    // Hiển thị danh sách tất cả các cuộc hẹn
	@GetMapping("/list")
    public String listAppointments(Model model, Principal principal) {
        // Lấy tên người dùng đang đăng nhập
        String username = principal.getName();

        // Kiểm tra vai trò của người dùng đăng nhập
        boolean isDoctor = false; // Giả sử mặc định là người dùng không phải là bác sĩ
        for (GrantedAuthority authority : ((Authentication) principal).getAuthorities()) {
            if (authority.getAuthority().equals("DOCTOR")) {
                isDoctor = true;
                break;
            }
        }

        List<AppointmentNoLogin> appointments;
        if (isDoctor) {
            // Nếu người dùng có vai trò là DOCTOR, lấy các cuộc hẹn mà bác sĩ đó được phụ trách
            User doctor = userRepository.getUserByEmail(username);
            LocalDate currentDate = LocalDate.now(); // Lấy ngày hiện tại
            appointments = appointmentService.getAppointmentsByDoctor(doctor, currentDate);
        } else {    
            // Nếu người dùng không phải là DOCTOR, lấy tất cả các cuộc hẹn
            appointments = appointmentService.getAllAppointments();
        }

        // Thêm danh sách cuộc hẹn vào model
        model.addAttribute("appointments", appointments);

        return "/apps/appointmentNoLogin/appointments";
    }


    // Đoạn mã mẫu



    // Hiển thị biểu mẫu tạo mới cuộc hẹn
    @GetMapping("/new")
    public String showNewAppointmentForm(Model model) {
        model.addAttribute("appointment", new AppointmentNoLogin());
        // Lấy danh sách các chuyên khoa từ phương thức tùy chỉnh
        List<Specialty> specialties = appointmentRepository.findAllSpecialties();
        model.addAttribute("specialties", specialties);
        return "apps/appointmentNoLogin/newAppointmentNoLogin";
    }


    // Xử lý thông tin biểu mẫu tạo mới cuộc hẹn
    @PostMapping("/save")
    public String saveAppointment(@ModelAttribute("appointment") AppointmentNoLogin appointment, HttpServletRequest request) {
        Specialty specialty = appointment.getSpecialty();
        if (specialty != null && specialty.getId() != null) {
            // Đối tượng Specialty đã được gắn phiên định danh Hibernate, có thể lưu AppointmentNoLogin
            appointmentService.saveAppointment(appointment);
            
            // Gửi email xác thực
            //endVerificationEmail(appointment, request);
            
            // Hiển thị trang thành công
            return "apps/appointmentNoLogin/dangkysuccess";
        } else {
            // Xử lý lỗi hoặc hiển thị thông báo cho người dùng
            return "errorPage";
        }
    }
    @PostMapping("/save/order")
    public String saveAppointmentorder0(@ModelAttribute("appointment") AppointmentNoLogin appointment, HttpServletRequest request,Model model) {
        String patientEmail = appointment.getEmail();
        LocalDate today = LocalDate.now();
        Long count = appointmentRepository.countByEmailAndCreateOn(patientEmail, today);
        
        if (count != null && count > 0) {
        	model.addAttribute("error", "Bạn chỉ được gửi một cuộc hẹn mỗi ngày.");
            // Email đã được gửi trong ngày hôm nay, xử lý tương ứng
            return "/error/error";
        } else {
            // Email chưa được gửi trong ngày hôm nay, tiếp tục xử lý
            // Đoạn code xử lý lưu và gửi email xác thực
            appointment.setOrderNumber(0);
            appointmentRepository.save(appointment);
            sendVerificationEmail(appointment, request);
            return "apps/appointmentNoLogin/verify_email";
        }
    }
    @GetMapping("/save/order/{id}")
    public String saveAppointmentorder1(@PathVariable("id") Long id, Model model) {
    	 AppointmentNoLogin appointment = appointmentService.getAppointmentById(id)
                 .orElseThrow(() -> new IllegalArgumentException("Invalid appointment id: " + id));
    	Specialty specialty = appointment.getSpecialty();
        if (specialty != null && specialty.getId() != null) {
        	appointment.setOrderNumber(1);
            // Đối tượng Specialty đã được gắn phiên định danh Hibernate, có thể lưu AppointmentNoLogin
            appointmentService.saveAppointment(appointment);
            
            
            // Hiển thị trang thành công
            return "apps/appointmentNoLogin/dangkysuccess";
        } else {
            // Xử lý lỗi hoặc hiển thị thông báo cho người dùng
            return "errorPage";
        }
    }
    private void sendVerificationEmail(AppointmentNoLogin appointment, HttpServletRequest request) {
        try {
            String verificationLink = generateVerificationLink(appointment, request);
            String patientEmail = appointment.getEmail();
            
            // Tạo và gửi email xác thực với liên kết xác thực đến địa chỉ email của người dùng
            String emailContent = "Vui lòng xác thực tài khoản của bạn bằng cách nhấp vào liên kết sau: " + verificationLink;
            emailService.sendEmail(patientEmail, "Xác thực tài khoản", emailContent);
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý khi gặp lỗi khi gửi email
        }
    }

    private String generateVerificationLink(AppointmentNoLogin appointment, HttpServletRequest request) {
        String token = generateRandomToken(); // Tạo mã ngẫu nhiên
        saveTokenToDatabase(appointment, token); // Lưu token vào cơ sở dữ liệu

        String baseUrl = getBaseUrl(request);
        String link = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
        link += "appointments/save/order/" + appointment.getId(); // Sử dụng giá trị thực của id

        return link;
    }
    private String generateRandomToken() {
        // Tạo mã ngẫu nhiên ở đây
        return "abc123"; // Đây là ví dụ mã ngẫu nhiên cứng
    }

    private void saveTokenToDatabase(AppointmentNoLogin appointment, String token) {
        // Lưu token vào cơ sở dữ liệu ở đây
        // Ví dụ: appointmentService.saveVerificationToken(appointment, token);
    }

    private String getBaseUrl(HttpServletRequest request) {
        // Lấy baseUrl của ứng dụng từ request
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        return scheme + "://" + serverName + ":" + serverPort + contextPath;
    }



    // Hiển thị biểu mẫu cập nhật cuộc hẹn
    @GetMapping("/edit/{id}")
    public String showEditAppointmentForm(@PathVariable("id") Long id, Model model) {
        AppointmentNoLogin appointment = appointmentService.getAppointmentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid appointment id: " + id));
        model.addAttribute("appointment", appointment);
        LocalDate appointmentDate = appointment.getAppointmentDate();
	       Period appointmentPeriod = appointment.getAppointmentPeriod();
	       Specialty specialty = appointment.getSpecialty();

        // Lấy danh sách bác sĩ theo chuyên khoa của cuộc hẹn
	       List<User> doctors = scheduleItemRepository.findDoctorsByWorkdateAndAppointmentPeriodAndSpecialty(appointmentDate, appointmentPeriod, specialty.getId());
	       model.addAttribute("doctors", doctors);

        return "apps/appointmentNoLogin/editAppointmentNoLogin";
    }

    // Xử lý thông tin biểu mẫu cập nhật cuộc hẹn
    @PostMapping("/update")
    public String updateAppointment(HttpServletRequest request, @ModelAttribute("appointmentNoLogin") AppointmentNoLogin appointmentNoLogin, Model model) {
        String idStr = request.getParameter("id");
        Long appointmentId = Long.valueOf(idStr);
        appointmentService.updateAppointment(appointmentId, appointmentNoLogin);
        return "redirect:/appointments/list";
    }


    // Xóa cuộc hẹn
    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable("id") Long id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/appointments/list";
    }
}
