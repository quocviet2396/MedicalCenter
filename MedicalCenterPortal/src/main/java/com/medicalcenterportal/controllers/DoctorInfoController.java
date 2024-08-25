package com.medicalcenterportal.controllers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.medicalcenter.entities.DoctorInfo;
import com.medicalcenter.entities.Specialty;
import com.medicalcenter.paging.PagingAndSortingHelper;
import com.medicalcenter.paging.PagingAndSortingParam;
import com.medicalcenter.services.DoctorInfoService;
import com.medicalcenter.services.SpecialtyService;
import com.medicalcenterportal.helpers.AppConstant;
import com.medicalcenterportal.helpers.AppHelper;
import com.medicalcenterportal.storage.StorageService;

@Controller
@RequestMapping("/doctorinfos")
public class DoctorInfoController {

	private String defaultRedirectURL = "redirect:/doctorinfos/page/1?searchText=";

	@Autowired
	private DoctorInfoService doctorInfoService;
	
	@Autowired
	private SpecialtyService specService;

	private final StorageService storageService;

	public DoctorInfoController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("")
	public String getDoctorInfoList(Model model) {
		List<DoctorInfo> listDoctorInfos = doctorInfoService.getAllDoctorInfo();
		List<Specialty> listSpecialties = specService.getAllSpecialty();

		model.addAttribute("listDoctorInfos", listDoctorInfos);
		model.addAttribute("listSpecialties", listSpecialties);

		model.addAttribute("currentPage", "1");
		model.addAttribute("searchText", "");
		model.addAttribute("moduleURL", "/doctorinfos");

		return "/apps/doctorinfos/list";
	}

	@GetMapping("/page")
	public String listByPage() {
		return defaultRedirectURL;
	}

	@GetMapping("/page/{pageNum}")
	public String listByPage(
			@PagingAndSortingParam(listName = "listDoctorInfos", moduleURL = "/doctorinfos", defaultSortField = "id") PagingAndSortingHelper helper,
			@PathVariable(name = "pageNum") int pageNum) {

		doctorInfoService.listDoctorInfoByPage(pageNum, AppConstant.pageCount, helper);

		return "/apps/doctorinfos/list";
	}

	@GetMapping("/add")
	public String showAddDoctorInfoForm(Model model) {
		List<Specialty> listSpecialties = specService.getAllSpecialty();
		
		model.addAttribute("doctorInfo", new DoctorInfo());
		model.addAttribute("listSpecialties", listSpecialties);
		return "/apps/doctorinfos/add";
	}

	@PostMapping("/add")
	public String saveDoctorInfo(@RequestParam("picture") MultipartFile file, DoctorInfo doctorInfo,
			RedirectAttributes redirectAttributes) {

		if (!file.isEmpty()) {
			String fileName = AppHelper.encode(doctorInfo.getTitle());
			doctorInfo.setPhoto(fileName);
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

		doctorInfoService.createDoctorInfo(doctorInfo);

		redirectAttributes.addFlashAttribute("message", "The new Doctor Info has been added successfully.");

		return "redirect:/doctorinfos/page/1";
	}

	// Update doctor info
	@GetMapping("/edit/{id}")
	public String editDoctorInfo(@PathVariable("id") Long id, Model model) {
		DoctorInfo doctorInfo = doctorInfoService.getDoctorInfoById(id);
		List<Specialty> listSpecialties = specService.getAllSpecialty();

		model.addAttribute("doctorInfo", doctorInfo);
		model.addAttribute("listSpecialties", listSpecialties);
		return "/apps/doctorinfos/edit";
	}

	@PostMapping("/edit/{id}")
	public String updateDoctorInfo(@RequestParam("picture") MultipartFile file, DoctorInfo doctorInfo,
			RedirectAttributes redirectAttributes, Model model) {
		if (!file.isEmpty()) {
			String fileName = AppHelper.encode(doctorInfo.getTitle());
			doctorInfo.setPhoto(fileName);
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

		doctorInfoService.updateDoctorInfo(doctorInfo);

		redirectAttributes.addFlashAttribute("message", "The Doctor Info has been updated successfully.");

		return "redirect:/doctorinfos/page/1";
	}
	
}