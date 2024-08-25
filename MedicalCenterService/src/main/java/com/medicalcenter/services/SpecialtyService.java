package com.medicalcenter.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicalcenter.entities.Specialty;
import com.medicalcenter.paging.PagingAndSortingHelper;
import com.medicalcenter.repositories.SpecialtyRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SpecialtyService {

	@Autowired
	private SpecialtyRepository specRepo;
	
	public void listSpecialtyByPage(int pageNum, int pageCount, PagingAndSortingHelper helper) {
		helper.listEntities(pageNum, pageCount, specRepo);
	}
	
	public List<Specialty> getAllSpecialty() {
		
		return (List<Specialty>) specRepo.findAll();
	}
	
	
	public Specialty getSpecialtyById(Long id) {
        return specRepo.findById(id).orElse(null);
    }

    public Specialty createSpecialty(Specialty specialty) {
    	specialty.setCreateOn(LocalDateTime.now());
        return specRepo.save(specialty);
    }

    public Specialty updateSpecialty(Specialty specialtyDto) {
    	Specialty specialtyInDB = specRepo.findById(specialtyDto.getId()).get();
    	specialtyInDB.setNameSpecialty(specialtyDto.getNameSpecialty());
    	specialtyInDB.setDescription(specialtyDto.getDescription());
    	if (specialtyDto.getPhoto() != null) {
			specialtyInDB.setPhoto(specialtyDto.getPhoto());
		}
    	specialtyInDB.setUpdateOn(LocalDateTime.now());
        return specRepo.save(specialtyInDB);
    }

    public void deleteSpecialty(Long id) {
    	specRepo.deleteById(id);
    }

    public Specialty findByName(String nameSpecialty) {
        return specRepo.findByNameSpecialty(nameSpecialty);
    }
}
