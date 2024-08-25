package com.medicalcenter.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicalcenter.dto.MedicalAndMedicineRecordDTO;
import com.medicalcenter.entities.MedicalRecord;
import com.medicalcenter.entities.Medicine;
import com.medicalcenter.entities.MedicineRecord;
import com.medicalcenter.entities.User;
import com.medicalcenter.exceptions.UserNotFoundException;
import com.medicalcenter.repositories.MedicineRepository;
import com.medicalcenter.repositories.MedicinesRecordRepository;

import jakarta.transaction.Transactional;
@Service
@Transactional
public class MedicineRecordService {

	private final MedicinesRecordRepository medicineRecordRepository;
	
	
	private final MedicineRepository medicineRepository;

   
    public MedicineRecordService(MedicinesRecordRepository medicineRecordRepository ,MedicineRepository medicineRepository) {
        this.medicineRecordRepository = medicineRecordRepository;
        this.medicineRepository = medicineRepository;
    }
	
    public MedicineRecord saveMedicineRecord(Long medicalRecordId, Long medicineId, String quantity, String note) {
        
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setId(medicalRecordId);

        Medicine medicine = new Medicine();
        medicine.setId(medicineId);

        MedicineRecord medicineRecord = new MedicineRecord();
        medicineRecord.setMedicalRecord(medicalRecord);
        medicineRecord.setMedicine(medicine);
        medicineRecord.setQuantity(quantity);
        medicineRecord.setNote(note);
        
       
        

        return medicineRecordRepository.save(medicineRecord);
    }
    
    public List<Medicine> searchMedicinesByKeyword(String searchText) {
        return medicineRepository.searchMedicinesByKeyword(searchText);
    }
    private String getFormNames(List<MedicineRecord> records) {
	     return records.stream().map(record -> record.getMedicine().getForm() + "\n\n").collect(Collectors.joining());
	 }
    public List<MedicalAndMedicineRecordDTO> getAllMedicalAndMedicineRecordsByDoctorId(Long doctorId) {
        List<MedicineRecord> medicineRecords = medicineRecordRepository.findByMedicalRecordDoctorUserId(doctorId);
        
        Map<Long, List<MedicineRecord>> groupedMedicineRecords = new HashMap<>();
        for (MedicineRecord medicineRecord : medicineRecords) {
            groupedMedicineRecords
                .computeIfAbsent(medicineRecord.getMedicalRecord().getId(), k -> new ArrayList<>())
                .add(medicineRecord);
        }
        
        return groupedMedicineRecords.values().stream().flatMap(records -> {
            MedicalRecord medicalRecord = records.get(0).getMedicalRecord(); 
            MedicalAndMedicineRecordDTO dto = new MedicalAndMedicineRecordDTO();
            dto.setId(records.get(0).getId()); 
            dto.setMedicineName(getMedicineNames(records));
            dto.setNote(getNotes(records));
            dto.setQuantity(getTotalQuantity(records));
           
            dto.setDescription(medicalRecord.getDescription());
            dto.setLastVisitDate(medicalRecord.getLastVisitDate());
            dto.setFollowUpDate(medicalRecord.getFollowUpDate());
            dto.setPatientName(medicalRecord.getPatientUser().getFullName());
            dto.setDoctorName(medicalRecord.getDoctorUser().getFullName());
            dto.setForm(getFormNames(medicineRecords));
            return Stream.of(dto);
        }).collect(Collectors.toList());
    }

 
   	 public List<MedicalAndMedicineRecordDTO>   getAllMedicalAndMedicineRecordsByUserId(Long userId) {
   	    List<MedicineRecord> medicineRecords = medicineRecordRepository.findByMedicalRecordPatientUserId(userId);
   		    
   		    Map<Long, List<MedicineRecord>> groupedMedicineRecords = new HashMap<>();
   		    for (MedicineRecord medicineRecord : medicineRecords) {
   		        groupedMedicineRecords
   		            .computeIfAbsent(medicineRecord.getMedicalRecord().getId(), k -> new ArrayList<>())
   		            .add(medicineRecord);
   		    }
   		    
   		    return groupedMedicineRecords.values().stream().flatMap(records -> {
   		        MedicalRecord medicalRecord = records.get(0).getMedicalRecord();
   		        MedicalAndMedicineRecordDTO dto = new MedicalAndMedicineRecordDTO();
   		        dto.setId(records.get(0).getId()); 
   		        dto.setMedicineName(getMedicineNames(records));
   		        dto.setNote(getNotes(records));
   		        dto.setQuantity(getTotalQuantity(records));
   		        dto.setDescription(medicalRecord.getDescription());
   		        dto.setLastVisitDate(medicalRecord.getLastVisitDate());
   		        dto.setFollowUpDate(medicalRecord.getFollowUpDate());
   		        dto.setPatientName(medicalRecord.getPatientUser().getFullName());
   		        dto.setDoctorName(medicalRecord.getDoctorUser().getFullName());
   		     dto.setForm(getFormNames(medicineRecords));
   		        return Stream.of(dto);
   		    }).collect(Collectors.toList());
   		}
    
	 
	 public List<MedicalAndMedicineRecordDTO> getAllMedicalAndMedicineRecords() {
		    List<MedicineRecord> medicineRecords = medicineRecordRepository.findAll();
		    
		    Map<Long, List<MedicineRecord>> groupedMedicineRecords = new HashMap<>();
		    for (MedicineRecord medicineRecord : medicineRecords) {
		        groupedMedicineRecords
		            .computeIfAbsent(medicineRecord.getMedicalRecord().getId(), k -> new ArrayList<>())
		            .add(medicineRecord);
		    }
		    
		    return groupedMedicineRecords.values().stream().flatMap(records -> {
		        MedicalRecord medicalRecord = records.get(0).getMedicalRecord(); 
		        MedicalAndMedicineRecordDTO dto = new MedicalAndMedicineRecordDTO();
		        dto.setId(records.get(0).getId()); 
		        dto.setMedicineName(records.get(0).getMedicine().getGenericName()); 
		        dto.setNote(getNotes(records));
		        dto.setQuantity(getTotalQuantity(records));
		       
		        dto.setDescription(medicalRecord.getDescription());
		        dto.setLastVisitDate(medicalRecord.getLastVisitDate());
		        dto.setFollowUpDate(medicalRecord.getFollowUpDate());
		        dto.setPatientName(medicalRecord.getPatientUser().getFullName());
		        dto.setDoctorName(medicalRecord.getDoctorUser().getFullName());
		        dto.setForm(getFormNames(medicineRecords));
		        return Stream.of(dto);
		    }).collect(Collectors.toList());
		}

	 private String getMedicineNames(List<MedicineRecord> records) {
	     return records.stream().map(record -> record.getMedicine().getGenericName() + "\n\n").collect(Collectors.joining());
	 }

	 private String getNotes(List<MedicineRecord> records) {
	     return records.stream().map(record -> record.getNote() + "\n\n").collect(Collectors.joining());
	 }

	 private String getTotalQuantity(List<MedicineRecord> records) {
	     return records.stream().map(record -> record.getQuantity() + "\n\n").collect(Collectors.joining());
	 }

	 
	 public MedicalAndMedicineRecordDTO getMedicalAndMedicineRecordById(Long id) {
		    Optional<MedicineRecord> medicineRecordOptional = medicineRecordRepository.findById(id);

		    if (medicineRecordOptional.isPresent()) {
		        MedicineRecord medicineRecord = medicineRecordOptional.get();
		        MedicalRecord medicalRecord = medicineRecord.getMedicalRecord();

		        List<MedicineRecord> medicineRecords = medicineRecordRepository.findByMedicalRecord_Id(medicalRecord.getId());

		        MedicalAndMedicineRecordDTO dto = new MedicalAndMedicineRecordDTO();
		        dto.setId(medicineRecord.getId());
		        dto.setMedicineName(getMedicineNames(medicineRecords));
		        dto.setNote(getNotes(medicineRecords));
		        dto.setQuantity(getTotalQuantity(medicineRecords));
		        dto.setDescription(medicalRecord.getDescription());
		        dto.setLastVisitDate(medicalRecord.getLastVisitDate());
		        dto.setFollowUpDate(medicalRecord.getFollowUpDate());
		        dto.setPatientName(medicalRecord.getPatientUser().getFullName());
		        dto.setDoctorName(medicalRecord.getDoctorUser().getFullName());
		        dto.setForm(getFormNames(medicineRecords));

		        return dto;
		    }

		    return null;
		}

	

}