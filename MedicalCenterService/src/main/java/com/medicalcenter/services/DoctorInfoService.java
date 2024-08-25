package com.medicalcenter.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicalcenter.entities.DoctorInfo;
import com.medicalcenter.paging.PagingAndSortingHelper;
import com.medicalcenter.repositories.DoctorInfoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DoctorInfoService {

	@Autowired
	private DoctorInfoRepository doctorInfoRepo;

	public void listDoctorInfoByPage(int pageNum, int pageCount, PagingAndSortingHelper helper) {
		helper.listEntities(pageNum, pageCount, doctorInfoRepo);
	}

	public DoctorInfo createDoctorInfo(DoctorInfo doctorInfo) {
		doctorInfo.setCreateOn(LocalDateTime.now());
		return doctorInfoRepo.save(doctorInfo);
	}

	public List<DoctorInfo> getAllDoctorInfo() {
		return (List<DoctorInfo>) doctorInfoRepo.findAll();
	}

	public DoctorInfo getDoctorInfoById(Long id) {
		return doctorInfoRepo.findById(id).orElse(null);
	}

	public List<DoctorInfo> filterBySpecialtyId(Long specialtyId) {
		return doctorInfoRepo.findBySpecialtyId(specialtyId);
	}

	public DoctorInfo updateDoctorInfo(DoctorInfo doctorInfoDto) {
		DoctorInfo doctorInfoInDB = doctorInfoRepo.findById(doctorInfoDto.getId()).get();
		doctorInfoInDB.setTitle(doctorInfoDto.getTitle());
		doctorInfoInDB.setContent(doctorInfoDto.getContent());
		doctorInfoInDB.setSpecialty(doctorInfoDto.getSpecialty());
		doctorInfoInDB.setUpdateOn(LocalDateTime.now());
		if (doctorInfoDto.getPhoto() != null) {
			doctorInfoInDB.setPhoto(doctorInfoDto.getPhoto());
		}

		return doctorInfoRepo.save(doctorInfoInDB);
	}

	public void deleteDoctorInfo(Long id) {
		doctorInfoRepo.deleteById(id);
	}

	public DoctorInfo findByTitle(String title) {
		return doctorInfoRepo.findByTitle(title);
	}

}