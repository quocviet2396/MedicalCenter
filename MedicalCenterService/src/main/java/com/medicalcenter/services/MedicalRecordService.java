package com.medicalcenter.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.medicalcenter.entities.MedicalRecord;
import com.medicalcenter.entities.Medicine;
import com.medicalcenter.entities.Specialty;
import com.medicalcenter.entities.User;
import com.medicalcenter.paging.PagingAndSortingHelper;
import com.medicalcenter.repositories.MedicalRecordRepository;
import com.medicalcenter.repositories.SpecialtyRepository;
import com.medicalcenter.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MedicalRecordService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private SpecialtyRepository specialtyRepo;
	
    @Autowired
    private MedicalRecordRepository medicalRecordRepo;
    
    public void listMedicalRecordByPage(int pageNum, int pageCount, PagingAndSortingHelper helper) {
		helper.listEntities(pageNum, pageCount, medicalRecordRepo);
		
	}
    
    public List<MedicalRecord> getAllMedicalRecord() {
		
		return (List<MedicalRecord>) medicalRecordRepo.findAll();
	}
    
    public List<MedicalRecord> getAllMedicalRecordsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return medicalRecordRepo.findByLastVisitDateBetween(startDate, endDate);
    }
	
    public void createMedicalRecord(Long patientUserId, Long doctorUserId, LocalDateTime followUpDate, LocalDateTime lastVisitDate, String description) {
        User patientUser = userRepo.findById(patientUserId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient user ID"));
        User doctorUser = userRepo.findById(doctorUserId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid doctor user ID"));
        
       

        MedicalRecord medicalRecord = new MedicalRecord(patientUser, doctorUser, description, followUpDate, lastVisitDate);

        medicalRecordRepo.save(medicalRecord);
    }
   

    public List<MedicalRecord> getAllMedicalRecordsByDoctorId(Long doctorId) {
        return medicalRecordRepo.getAllMedicalRecordsByDoctorId(doctorId);
    }
    public List<MedicalRecord> getAllMedicalRecordsByDoctorIdAndLastVisitDateBetween(Long doctorId, LocalDateTime startDate, LocalDateTime endDate) {
        return medicalRecordRepo.findByDoctorUserIdAndLastVisitDateBetween(doctorId, startDate, endDate);
    }

    public void deleteMedicalRecord(Long medicalRecordId) {
        medicalRecordRepo.deleteById(medicalRecordId);
    }

    public void editMedicalRecord(Long id, Long patientUserId, Long doctorUserId, LocalDateTime followUpDate, LocalDateTime lastVisitDate,
			String description) {
		MedicalRecord medicalRecord = medicalRecordRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid medical record ID"));
        User patientUser = userRepo.findById(patientUserId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient user ID"));
        User doctorUser = userRepo.findById(doctorUserId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid doctor user ID"));
        
        medicalRecord.setLastVisitDate(lastVisitDate);
        medicalRecord.setFollowUpDate(followUpDate);;
      
        medicalRecord.setDescription(description);
       
        
        medicalRecordRepo.save(medicalRecord);
		
	}
    
    @Autowired
    public MedicalRecordService(SpecialtyRepository specialtyRepository) {
        this.specialtyRepo = specialtyRepository;
    }
    
    public MedicalRecord getMedicalRecordById(Long id) throws NotFoundException {
        return medicalRecordRepo.findById(id)
                .orElseThrow(() -> new NotFoundException());
    }

    public Specialty getSpecialtyById(Long specialtyId) {
        return specialtyRepo.findById(specialtyId).orElse(null);
    }

    public List<MedicalRecord> getAllMedicalRecords(String sortField, String sortDir) {
        return medicalRecordRepo.findAll(Sort.by(Sort.Direction.fromString(sortDir), sortField));
    }
    
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepo.findAll();
    }
    
    public List<MedicalRecord> searchMedicalRecordsByKeyword(String searchText) {
        return medicalRecordRepo.searchMedicalRecordsByKeyword(searchText);
    }
   

	public Page<MedicalRecord> getAllMedicalRecords(Pageable pageable) {
        return medicalRecordRepo.findAll(pageable);

	}

	public Optional<MedicalRecord> findById(Long id) {
	    return medicalRecordRepo.findById(id);
	            
	}

	public MedicalRecord getMedicineById(Long id) {
        Optional<MedicalRecord> optionalMedicalRecord = medicalRecordRepo.findById(id);
        return optionalMedicalRecord.orElse(null);
    }


}
