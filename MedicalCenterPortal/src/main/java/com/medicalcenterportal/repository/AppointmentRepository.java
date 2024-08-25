package com.medicalcenterportal.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.medicalcenter.entities.Appointment;
import com.medicalcenter.entities.Specialty;
import com.medicalcenter.entities.User;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	@Query("SELECT s FROM Specialty s")
    List<Specialty> findAllSpecialties();
    
    @Query("SELECT DISTINCT u FROM User u JOIN FETCH u.specialty s WHERE s.id = :specialtyId")
    List<User> findDoctorsBySpecialty(@Param("specialtyId") Long specialtyId);
    List<Appointment> findByPatientUser(User patientUser);
    List<Appointment> getAppointmentsByPatientUser(User user);
    @Query("SELECT a FROM Appointment a WHERE a.doctor = :doctor AND a.appointmentDate = :currentDate")
    List<Appointment> findByDoctorAndDate(@Param("doctor") User doctor, @Param("currentDate") LocalDate currentDate);
   
    
    @Query("SELECT s FROM Specialty s WHERE s.id = :specialtyId")
    Specialty findSpecialtyById(@Param("specialtyId") Long specialtyId);
    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate = ?1")
    List<Appointment> findByAppointmentDate(LocalDate appointmentDate);
    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId")
    List<Appointment> findByDoctorId(@Param("doctorId") Long doctorId);
    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.patientUser = :patientUser AND DATE(a.createOn) = :createDate")
    long countByPatientUserAndCreateOn(@Param("patientUser") User patientUser, @Param("createDate") LocalDate createDate);
    List<Appointment> findByPatientUserId(Long patientId);
}
