package com.medicalcenterportal.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.medicalcenter.dto.MedicalAndMedicineRecordDTO;
import com.medicalcenter.entities.Role;
import com.medicalcenter.entities.Specialty;
import com.medicalcenter.entities.User;
import com.medicalcenter.exceptions.UserNotFoundException;
import com.medicalcenter.services.MedicineRecordService;
import com.medicalcenter.services.RoleService;
import com.medicalcenter.services.SpecialtyService;
import com.medicalcenter.services.UserService;
import com.medicalcenterportal.helpers.AppHelper;
import com.medicalcenterportal.security.AppUserDetails;
import com.medicalcenterportal.storage.StorageService;

@Controller
@RequestMapping("/profile")
public class ProfileController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private SpecialtyService specService;
	
	@Autowired
	private MedicineRecordService medicineRecordService;
	
	private final StorageService storageService;

	public ProfileController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping
	public String getUserInfo(@AuthenticationPrincipal AppUserDetails loggedUser, Model model) throws UserNotFoundException {
		List<Role> listRoles = roleService.getAllURoles();
		List<Specialty> listSpecialties = specService.getAllSpecialty();
		
		User user = userService.get(loggedUser.getId());
		
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("listSpecialties", listSpecialties);
		
		return "/profile/index";
	}
	
	@GetMapping("/listm")
	public String MedicineInfo(@AuthenticationPrincipal AppUserDetails loggedUser, Model model) throws UserNotFoundException {
	    List<Role> listRoles = roleService.getAllURoles();
	    
	    User user = userService.get(loggedUser.getId());
	    model.addAttribute("user", user);
	    model.addAttribute("listRoles", listRoles);
	    
	   
	    Long userId = loggedUser.getId();
	    
	    List<MedicalAndMedicineRecordDTO> records = medicineRecordService.getAllMedicalAndMedicineRecordsByUserId(userId);
	    
	    model.addAttribute("records", records);
	    
	    return "/profile/listm";
	}
	
	@PostMapping
	public String updateUserInfo(@RequestParam("avatar") MultipartFile file, User user, RedirectAttributes redirectAttributes, Model model) {
		System.out.println("User save: " + user.getFirstName() + " -- " + user.getLastName() + " -- " + user.getEmail()
				+ " -- " + user.isEnabled() + "-- Role : " +user.getRoles() + "--Address : " +user.getAddress());
		if (!file.isEmpty()) {
			String fileName = AppHelper.encode(user.getEmail());
			user.setPhoto(fileName);
			storageService.store(file, fileName);

			List<String> files = storageService.loadAll()
					.map(path -> MvcUriComponentsBuilder
							.fromMethodName(PortalController.class, "serveFile", path.getFileName().toString()).build()
							.toUri().toString())
					.collect(Collectors.toList());

			for (String filename : files) {
				System.out.println("Uploaded file: " + filename);
			}
		}

//		User findUser = userService.getByEmail(user.getEmail());

		
		userService.updateAccount(user);
		
		System.out.println("USER ADDRESS AFTER UPDATE: " +user.getAddress());
		
		redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");

		return "redirect:/profile";
	}
}
