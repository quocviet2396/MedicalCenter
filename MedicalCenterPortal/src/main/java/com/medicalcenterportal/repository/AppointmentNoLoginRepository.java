package com.medicalcenterportal.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import com.medicalcenter.entities.AppointmentNoLogin;
import com.medicalcenter.entities.Specialty;
import com.medicalcenter.entities.User;
import com.medicalcenterportal.services.AppointmentNoLoginServiceImpl;
public interface AppointmentNoLoginRepository extends JpaRepository<AppointmentNoLogin,Long> {
	  // Phương thức để lấy danh sách tất cả các chuyên khoa
	@Query("SELECT s FROM Specialty s")
    List<Specialty> findAllSpecialties();
	@Query("SELECT s FROM Specialty s WHERE s.id = :specialtyId")
    Specialty findSpecialtyById(@Param("specialtyId") Long specialtyId);
    @Query("SELECT DISTINCT u FROM User u JOIN FETCH u.specialty s WHERE s.id = :specialtyId")
    List<User> findDoctorsBySpecialty(@Param("specialtyId") Long specialtyId);
    @Query("SELECT a FROM AppointmentNoLogin a WHERE a.appointmentDate = ?1")
    List<AppointmentNoLogin> findByAppointmentDate(LocalDate appointmentDate);
    @Query("SELECT a FROM AppointmentNoLogin a WHERE a.doctor.id = :doctorId")
    List<AppointmentNoLogin> findByDoctorId(@Param("doctorId") Long doctorId);
    @Query("SELECT COUNT(a) FROM AppointmentNoLogin a WHERE a.email = :email AND DATE(a.createOn) = :date")
    Long countByEmailAndCreateOn(@Param("email") String email, @Param("date") LocalDate date);
    List<AppointmentNoLogin> findByDoctorAndAppointmentDate(User doctor, LocalDate appointmentDate);
}
