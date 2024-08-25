package com.medicalcenter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medicalcenter.entities.MedicalRecord;
import com.medicalcenter.entities.Medicine;
import com.medicalcenter.paging.PagingAndSortingHelper;
import com.medicalcenter.repositories.MedicineRepository;

import jakarta.transaction.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Transactional

public class MedicineService {

	@Autowired
	private  MedicineRepository medicineRepository;

    public MedicineService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }
    
    public List<Medicine> getAllMedicines(String sortField, String sortDir) {
        return medicineRepository.findAll(Sort.by(Sort.Direction.fromString(sortDir), sortField));
    }
    
    public List<Medicine> searchMedicinesByKeyword(String searchText) {
        return medicineRepository.searchMedicinesByKeyword(searchText);
    }
    
    public List<Medicine> searchMedicines(String searchText, String sortField, String sortDir) {
        if (searchText != null && !searchText.isEmpty()) {
            return medicineRepository.searchMedicinesByKeyword(searchText);
        } else {
            return getAllMedicines(sortField, sortDir);
        }
    }
    

	public void listMedicinesByPage(int pageNum, int pageCount, PagingAndSortingHelper helper) {
		helper.listEntities(pageNum, pageCount,medicineRepository);
	}
	
    
  
    public Medicine getMedicineById(Long id) {
        return medicineRepository.findById(id).orElse(null);
    }

    public void createMedicine(Long id,int _version, String genericName, String brandName,String measurementUnit,String form) {
    	
    	Medicine medicine = new Medicine(id, _version, genericName,brandName ,measurementUnit,form);
    	
        medicineRepository.save(medicine);
    }

    public void updateMedicine(Long id, int _version, String genericName, String brandName, String measurementUnit, String form) {
        Medicine medicine = medicineRepository.findById(id).orElse(null);
        
        if (medicine != null) {
            medicine.set_version(_version);
            medicine.setGenericName(genericName);
            medicine.setBrandName(brandName);
            medicine.setMeasurementUnit(measurementUnit);
            medicine.setForm(form);
            
            medicineRepository.save(medicine);
        }
    }
    
    public void deleteMedicine(Long id) {
        medicineRepository.deleteById(id);
    }
}
