package com.medicalcenterportal.controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ProcessBuilder.Redirect;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.io.IOUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.medicalcenter.entities.CombinedAppointment;
import com.medicalcenter.entities.User;

import com.medicalcenter.services.UserService;
import com.medicalcenterportal.services.AppointmentListOfDoctorService;

@Controller
@RequestMapping("/listappointmentofdoctor")
public class AppointmentListOfDoctorController {

    @Autowired
    private  AppointmentListOfDoctorService appointmentListOfDoctorService;
    @Autowired
    private UserService userService; 
    private 	List<CombinedAppointment> combinedAppointments;
    @GetMapping("/list")
    public String getAllAppointments(Model model, Principal principal) {
        // Lấy tên người dùng đang đăng nhập
        String username = principal.getName();

        // Kiểm tra xem người dùng có vai trò là bác sĩ hay không
        User doctor = userService.getByEmail(username);
        
            // Nếu người dùng có vai trò là bác sĩ, lấy danh sách các cuộc hẹn mà bác sĩ đó phụ trách
            List<CombinedAppointment> combinedAppointments = appointmentListOfDoctorService.getAppointmentsByDoctor(doctor.getId());

            // Thêm danh sách các cuộc hẹn vào model
            model.addAttribute("combinedAppointments", combinedAppointments);

            // Trả về view tương ứng
            return "/apps/listAppointmentofdoctor/appointmentlistofdoctor";
       
    }

    @PostMapping("/updateArrivedStatus")
    public String updateArrivedStatus(@RequestParam("appointmentId") Long appointmentId, @RequestParam("arrived") String arrived) {
    
    	try {
            // Cập nhật trạng thái "arrived" của cuộc hẹn
            appointmentListOfDoctorService.updateArrivedStatus(appointmentId, arrived);

           
          
            return "redirect:/listappointmentofdoctor/list";
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            e.printStackTrace();
            return "Error occurred while updating arrived status";
        }
    }
    @GetMapping("/list/pdf")
    public ResponseEntity<InputStreamResource> exportToPDF(Model model, Principal principal) {
        try {
            String username = principal.getName();
            User doctor = userService.getByEmail(username);
            List<CombinedAppointment> combinedAppointments = appointmentListOfDoctorService
                    .getAppointmentsByDoctor(doctor.getId());

            // Tạo một tài liệu PDF
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();

            // Sử dụng font Arial Unicode MS
            String fontPath = "/static/assets/fonts/arial-unicode-ms.ttf"; // Đường dẫn tới tệp Arial Unicode MS.ttf

            // Tạo BaseFont từ tệp font
            BaseFont bf = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            // Tạo Font từ BaseFont với kích thước là 12
            Font font = new Font(bf, 12);

            // Thêm dữ liệu từ danh sách cuộc hẹn vào tài liệu PDF
            for (CombinedAppointment appointment : combinedAppointments) {
                document.add(new Paragraph("Tên bệnh nhân: " + appointment.getFullName(), font));
                document.add(new Paragraph("Ngày sinh: " + appointment.getBirthday(), font));
                document.add(new Paragraph("Email: " + appointment.getEmail(), font));
                document.add(new Paragraph("Bác sĩ: " + appointment.getDoctorName(), font));
                document.add(new Paragraph("Ngày hẹn: " + appointment.getAppointmentDate(), font));
                document.add(new Paragraph("Chuyên khoa: " + appointment.getSpecialtyName(), font));
                document.add(new Paragraph("Triệu chứng: " + appointment.getSymptoms(), font));
                document.add(new Paragraph("Kham: " + appointment.getArrived(), font));
                // Thêm các thông tin khác tương tự ở đây
                document.add(new Paragraph("----------------------------------", font));
            }

            document.close();

            // Tạo một InputStreamResource từ ByteArrayOutputStream
            InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(baos.toByteArray()));

            // Trả về response có chứa file PDF
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment;filename=appointment_list.pdf").contentType(MediaType.APPLICATION_PDF).body(resource);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

