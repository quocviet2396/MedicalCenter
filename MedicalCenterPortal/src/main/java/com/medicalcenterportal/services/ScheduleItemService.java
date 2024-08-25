package com.medicalcenterportal.services;

import java.time.LocalDate;
import java.util.List;

import com.medicalcenter.entities.ScheduleItem;
import com.medicalcenter.entities.User;

public interface ScheduleItemService {
	 void saveScheduleItem(ScheduleItem scheduleItem);
	  ScheduleItem getScheduleItemById(Long id);
	  List<ScheduleItem> getAllScheduleItems();
	    
	    List<ScheduleItem> getScheduleItemsByDoctorId(Long doctorId);
		void updateScheduleItem(ScheduleItem scheduleItem);
		List<ScheduleItem> getScheduleItemsByDoctor(User doctor);
		List<ScheduleItem> getScheduleItemsByWorkdate(LocalDate workdate);
		 List<ScheduleItem> getScheduleItemsByWorkdateAndSpecialty(LocalDate workdate, String specialty);
}
