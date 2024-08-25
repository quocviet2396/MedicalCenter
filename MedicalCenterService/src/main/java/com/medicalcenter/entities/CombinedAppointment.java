package com.medicalcenter.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.medicalcenter.enums.Period;

import lombok.Data;

@Data
public class CombinedAppointment {
    private Long id; // ID của cuộc hẹn
    private String fullName; 
    private String birthday;
    private String email;
    // Tên đầy đủ của bệnh nhân hoặc người đặt hẹn
    private String doctorName; // Tên đầy đủ của bác sĩ
    private LocalDate appointmentDate; // Ngày hẹn
    private Period appointmentPeriod; // Thời gian hẹn
    private String specialtyName; // Tên chuyên khoa
    private String symptoms; 
    private String arrived;
    private LocalDateTime createOn;// Các triệu chứng
}
