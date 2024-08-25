package com.medicalcenterportal.controllers;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.medicalcenterportal.helpers.AppConstant;
import com.medicalcenterportal.security.AppUserDetails;
import com.medicalcenterportal.storage.StorageService;
import com.medicalcenter.entities.Blog;
import com.medicalcenter.entities.DoctorInfo;
import com.medicalcenter.entities.Role;
import com.medicalcenter.entities.Specialty;
import com.medicalcenter.entities.User;
import com.medicalcenter.paging.PagingAndSortingHelper;
import com.medicalcenter.paging.PagingAndSortingParam;
import com.medicalcenter.services.BlogService;
import com.medicalcenter.services.DoctorInfoService;
import com.medicalcenter.services.RoleService;
import com.medicalcenter.services.SpecialtyService;
import com.medicalcenter.services.UserService;

import jakarta.validation.Valid;

@Controller
public class PortalController {

	private String defaultBlogsRedirectURL = "redirect:/blogsgrid/page/1?searchText=";
	private String defaultDoctorInfosRedirectURL = "redirect:/doctorinfosgrid/page/1?searchText=";
	private String defaultSpecialtiesRedirectURL = "redirect:/specialtiesgrid/page/1?searchText=";

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private BlogService blogService;

	@Autowired
	private SpecialtyService specService;

	@Autowired
	private DoctorInfoService doctorInfoService;
	

	private final StorageService storageService;

	public PortalController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable("filename") String filename) {

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@GetMapping("/")
	public String getHome(Model model, @AuthenticationPrincipal AppUserDetails userDetails) {
		if (userDetails != null) {
			model.addAttribute("isLoggedIn", true);
		} else {
			model.addAttribute("isLoggedIn", false);
		}
		return "index";
	}

	// Begin-DoctorInfo
	@GetMapping("/doctorinfosgrid")
	public String getDoctorInfoView(Model model, @AuthenticationPrincipal AppUserDetails userDetails) {
		if (userDetails != null) {
			model.addAttribute("isLoggedIn", true);
		} else {
			model.addAttribute("isLoggedIn", false);
		}
		List<DoctorInfo> listDoctorInfos = doctorInfoService.getAllDoctorInfo();
		List<Specialty> listSpecialties = specService.getAllSpecialty();
		model.addAttribute("listDoctorInfos", listDoctorInfos);
		model.addAttribute("listSpecialties", listSpecialties);
		model.addAttribute("currentPage", "1");
		model.addAttribute("searchText", "");
		model.addAttribute("moduleURL", "/doctorinfosgrid");
		return "doctorinfosview";
	}

	@GetMapping("/doctorinfosgrid/filter")
	public String filterDoctorInfosBySpecialty(@RequestParam(value = "specialtyId", required = false) Long specialtyId,
			Model model, @AuthenticationPrincipal AppUserDetails userDetails) {
		if (userDetails != null) {
			model.addAttribute("isLoggedIn", true);
		} else {
			model.addAttribute("isLoggedIn", false);
		}

		List<DoctorInfo> filteredDoctorInfos;
		if (specialtyId != null) {
			filteredDoctorInfos = doctorInfoService.filterBySpecialtyId(specialtyId);
		} else {
			filteredDoctorInfos = doctorInfoService.getAllDoctorInfo();
		}

		List<Specialty> listSpecialties = specService.getAllSpecialty();

		model.addAttribute("listDoctorInfos", filteredDoctorInfos);
		model.addAttribute("listSpecialties", listSpecialties);
		model.addAttribute("currentPage", "1");
		model.addAttribute("searchText", "");
		model.addAttribute("moduleURL", "/doctorinfosgrid");

		return "doctorinfosview";
	}

	@GetMapping("doctorinfosgrid/page")
	public String listDoctorInfosByPage() {
		return defaultDoctorInfosRedirectURL;
	}

	@GetMapping("/doctorinfosgrid/page/{pageNum}")
	public String listDoctorInfosByPage(Model model, @AuthenticationPrincipal AppUserDetails userDetails,
			@PagingAndSortingParam(listName = "listDoctorInfos", moduleURL = "/doctorinfosgrid", defaultSortField = "title") PagingAndSortingHelper helper,
			@PathVariable(name = "pageNum") int pageNum) {
		if (userDetails != null) {
			model.addAttribute("isLoggedIn", true);
		} else {
			model.addAttribute("isLoggedIn", false);
		}
		List<Specialty> listSpecialties = specService.getAllSpecialty();
		model.addAttribute("listSpecialties", listSpecialties);
		doctorInfoService.listDoctorInfoByPage(pageNum, AppConstant.pageCount, helper);

		return "doctorinfosview";
	}

	@GetMapping("/doctorinfosgrid/details/{id}")
	public String getDoctorInfoDetails(@PathVariable("id") Long id, Model model,
			@AuthenticationPrincipal AppUserDetails userDetails) {
		if (userDetails != null) {
			model.addAttribute("isLoggedIn", true);
		} else {
			model.addAttribute("isLoggedIn", false);
		}
		DoctorInfo doctorInfo = doctorInfoService.getDoctorInfoById(id);

		model.addAttribute("doctorInfo", doctorInfo);
		return "doctorinfosdetails";
	}

	// End-DoctorInfo

	// Begin-Blogs
	@GetMapping("/blogsgrid")
	public String getBlogView(Model model, @AuthenticationPrincipal AppUserDetails userDetails) {
		if (userDetails != null) {
			model.addAttribute("isLoggedIn", true);
		} else {
			model.addAttribute("isLoggedIn", false);
		}
		List<Blog> listBlogs = blogService.getAllBlog();
		Collections.reverse(listBlogs);
		model.addAttribute("listBlogs", listBlogs);
		model.addAttribute("currentPage", "1");
		model.addAttribute("searchText", "");
		model.addAttribute("moduleURL", "/blogsgrid");
		return "blogsview";
	}

	@GetMapping("/blogsgrid/page")
	public String listBlogsByPage() {
		return defaultBlogsRedirectURL;
	}

	@GetMapping("/blogsgrid/page/{pageNum}")
	public String listBlogsByPage(Model model, @AuthenticationPrincipal AppUserDetails userDetails,
			@PagingAndSortingParam(listName = "listBlogs", moduleURL = "/blogsgrid", defaultSortField = "id") PagingAndSortingHelper helper,
			@PathVariable(name = "pageNum") int pageNum) {
		if (userDetails != null) {
			model.addAttribute("isLoggedIn", true);
		} else {
			model.addAttribute("isLoggedIn", false);
		}
		blogService.listBlogByPage(pageNum, AppConstant.pageCount, helper);
	
		return "blogsview";
	}

	@GetMapping("/blogsgrid/details/{id}")
	public String getBlogDetails(@PathVariable("id") Long id, Model model,
			@AuthenticationPrincipal AppUserDetails userDetails) {
		if (userDetails != null) {
			model.addAttribute("isLoggedIn", true);
		} else {
			model.addAttribute("isLoggedIn", false);
		}
		Blog blog = blogService.getBlogById(id);

		model.addAttribute("blog", blog);
		return "blogdetails";
	}
	// End-Blogs
	
	// Begin-Specialties
		@GetMapping("/specialtiesgrid")
		public String getSpecialtyView(Model model, @AuthenticationPrincipal AppUserDetails userDetails) {
			if (userDetails != null) {
				model.addAttribute("isLoggedIn", true);
			} else {
				model.addAttribute("isLoggedIn", false);
			}
			List<Specialty> listSpecialties = specService.getAllSpecialty();
			model.addAttribute("listSpecialties", listSpecialties);
			model.addAttribute("currentPage", "1");
			model.addAttribute("searchText", "");
			model.addAttribute("moduleURL", "/specialtiesgrid");
			return "specialtiesview";
		}

		@GetMapping("/specialtiesgrid/page")
		public String listSpecialtiesByPage() {
			return defaultSpecialtiesRedirectURL;
		}

		@GetMapping("/specialtiesgrid/page/{pageNum}")
		public String listSpecialtiesByPage(Model model, @AuthenticationPrincipal AppUserDetails userDetails,
				@PagingAndSortingParam(listName = "listSpecialties", moduleURL = "/specialtiesgrid", defaultSortField = "nameSpecialty") PagingAndSortingHelper helper,
				@PathVariable(name = "pageNum") int pageNum) {
			if (userDetails != null) {
				model.addAttribute("isLoggedIn", true);
			} else {
				model.addAttribute("isLoggedIn", false);
			}
			specService.listSpecialtyByPage(pageNum, AppConstant.pageCount, helper);
			
			return "specialtiesview";
		}

		@GetMapping("/specialtiesgrid/details/{id}")
		public String getSpecialtyDetails(@PathVariable("id") Long id, Model model,
				@AuthenticationPrincipal AppUserDetails userDetails) {
			if (userDetails != null) {
				model.addAttribute("isLoggedIn", true);
			} else {
				model.addAttribute("isLoggedIn", false);
			}
			Specialty specialty = specService.getSpecialtyById(id);
			
			model.addAttribute("specialty", specialty);
			return "specialtiesdetails";
		}
		// End-Blogs

	@GetMapping("/lang")
	public String getLang(Model model) {
		return "index";
	}

	@GetMapping("/login")
	public String getLogin(Model model, @AuthenticationPrincipal AppUserDetails userDetails) {
		if (userDetails != null) {
			model.addAttribute("isLoggedIn", true);
		} else {
			model.addAttribute("isLoggedIn", false);
		}
		model.addAttribute("user", new User());
		return "/authentication/sign-in";
	}

	@GetMapping("/register")
	public String getRegister(Model model) {
		model.addAttribute("user", new User());

		return "/authentication/sign-up";
	}

	@PostMapping("/register")
	public String createCustomer(@Valid User user, BindingResult bindingResult, Model model) {
		System.out.println("User: " + user.getFirstName() + " -- " + user.getLastName());
		if (bindingResult.hasErrors()) {
			return "/authentication/sign-up";
		}
		Role patientRole = roleService.findByName("PATIENT");
		if (patientRole != null) {
			user.addRole(patientRole);
		}
		user.setChangePassword(false);
		user.setEnabled(true);
		user.setCreateOn(LocalDateTime.now());
		userService.save(user);

		return "redirect:/login";
	}
}