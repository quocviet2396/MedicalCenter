package com.medicalcenterportal.controllers;

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

import com.medicalcenter.entities.Specialty;
import com.medicalcenter.paging.PagingAndSortingHelper;
import com.medicalcenter.paging.PagingAndSortingParam;
import com.medicalcenter.services.SpecialtyService;
import com.medicalcenterportal.helpers.AppConstant;
import com.medicalcenterportal.helpers.AppHelper;
import com.medicalcenterportal.storage.StorageService;

@Controller
@RequestMapping("/specialties")
public class SpecialtyController {

	private String defaultRedirectURL = "redirect:/specialties/page/1?searchText=";
	
	@Autowired
	private SpecialtyService specService;
	
	private final StorageService storageService;

	public SpecialtyController(StorageService storageService) {
		this.storageService = storageService;
	}
	
	@GetMapping("")
	public String getRoleList(Model model) {
		List<Specialty> listSpecialties = specService.getAllSpecialty();

		model.addAttribute("listSpecialties", listSpecialties);

		model.addAttribute("currentPage", "1");
		model.addAttribute("searchText", "");
		model.addAttribute("moduleURL", "/specialties");

		return "/apps/specialties/list";
	}

	@GetMapping("/page")
	public String listByPage() {
		return defaultRedirectURL;
	}

	@GetMapping("/page/{pageNum}")
	public String listByPage(
			@PagingAndSortingParam(listName = "listSpecialties", moduleURL = "/specialties", defaultSortField = "id") PagingAndSortingHelper helper,
			@PathVariable(name = "pageNum") int pageNum) {

		specService.listSpecialtyByPage(pageNum, AppConstant.pageCount, helper);

		return "/apps/specialties/list";
	}
	
	@GetMapping("/add")
    public String showAddSpecialtyForm(Model model) {
        model.addAttribute("specialty", new Specialty());
        return "/apps/specialties/add";
    }

	@PostMapping("/add")
	public String addSpecialty(@RequestParam("picture") MultipartFile file, Specialty specialty,
	        RedirectAttributes redirectAttributes) {

	    if (!file.isEmpty()) {
	        String fileName = AppHelper.encode(specialty.getNameSpecialty());
	        specialty.setPhoto(fileName);
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

	    specService.createSpecialty(specialty);

	    redirectAttributes.addFlashAttribute("message", "The new Specialty has been added successfully.");

	    return "redirect:/specialties/page/1";
	}

    @GetMapping("/edit/{id}")
    public String showEditSpecialtyForm(@PathVariable("id") Long id, Model model) {
        Specialty specialty = specService.getSpecialtyById(id);
        if (specialty == null) {
            // Handle the case when the role is not found
            return defaultRedirectURL;
        }
        model.addAttribute("specialty", specialty);
        return "/apps/specialties/edit";
    }

    @PostMapping("/edit/{id}")
    public String editSpecialty(@RequestParam("picture") MultipartFile file, Specialty specialty,
            RedirectAttributes redirectAttributes, Model model) {

        if (!file.isEmpty()) {
            String fileName = AppHelper.encode(specialty.getNameSpecialty());
            specialty.setPhoto(fileName);
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

        specService.updateSpecialty(specialty);

        redirectAttributes.addFlashAttribute("message", "The specialty has been updated successfully.");

        return "redirect:/specialties/page/1";
    }

    @GetMapping("/delete/{id}")
    public String deleteSpecialty(@PathVariable("id") Long id) {
    	specService.deleteSpecialty(id);
        return "redirect:/specialties";
    }
}
