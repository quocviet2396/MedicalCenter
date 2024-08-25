package com.medicalcenterportal.services;

import java.util.List;
import java.util.Optional;

import com.medicalcenter.entities.FollowUpNoLogin;
import com.medicalcenter.entities.User;

public interface FollowUpNologinService {
	 List<FollowUpNoLogin> findAll();
	 FollowUpNoLogin updateFollowUpNoLogin(Long id, FollowUpNoLogin followUpNoLogin);
	 Optional<FollowUpNoLogin> findById(Long id);
	 List<FollowUpNoLogin> findByDoctor(User doctor);
}
