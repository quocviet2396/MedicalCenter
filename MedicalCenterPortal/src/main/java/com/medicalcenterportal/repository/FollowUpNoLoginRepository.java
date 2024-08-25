package com.medicalcenterportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicalcenter.entities.FollowUpNoLogin;
import com.medicalcenter.entities.User;

public interface FollowUpNoLoginRepository  extends JpaRepository<FollowUpNoLogin, Long> {   
List<FollowUpNoLogin>  findByDoctorUser(User doctor);
}
