package com.medicalcenterportal.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

import com.medicalcenter.entities.Blog;
import com.medicalcenter.paging.PagingAndSortingHelper;
import com.medicalcenter.paging.PagingAndSortingParam;
import com.medicalcenter.services.BlogService;
import com.medicalcenterportal.helpers.AppConstant;
import com.medicalcenterportal.helpers.AppHelper;
import com.medicalcenterportal.security.AppUserDetails;
import com.medicalcenterportal.storage.StorageService;

import jakarta.mail.MessagingException;

@Controller
@RequestMapping("/blogs")
public class BlogController {

	private String defaultRedirectURL = "redirect:/blogs/page/1?searchText=";

	@Autowired
	private BlogService blogService;

	private final StorageService storageService;

	public BlogController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("")
	public String getBlogList(Model model) {
		List<Blog> listBlogs = blogService.getAllBlog();

		model.addAttribute("listBlogs", listBlogs);

		model.addAttribute("currentPage", "1");
		model.addAttribute("searchText", "");
		model.addAttribute("moduleURL", "/blogs");

		return "/apps/blogs/list";
	}

	@GetMapping("/page")
	public String listByPage() {
		return defaultRedirectURL;
	}

	@GetMapping("/page/{pageNum}")
	public String listByPage(
			@PagingAndSortingParam(listName = "listBlogs", moduleURL = "/blogs", defaultSortField = "title") PagingAndSortingHelper helper,
			@PathVariable(name = "pageNum") int pageNum) {

		blogService.listBlogByPage(pageNum, AppConstant.pageCount, helper);

		return "/apps/blogs/list";
	}

	@GetMapping("/add")
	public String showAddBlogForm(Model model) {
		model.addAttribute("blog", new Blog());
		return "/apps/blogs/add";
	}

	@PostMapping("/add")
	public String saveBlog(@RequestParam("picture") MultipartFile file, Blog blog,
			RedirectAttributes redirectAttributes) {

		if (!file.isEmpty()) {
			String fileName = AppHelper.encode(blog.getTitle());
			blog.setPhoto(fileName);
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

		blogService.createBlog(blog);

		redirectAttributes.addFlashAttribute("message", "The new Blog has been added successfully.");

		return "redirect:/blogs/page/1";
	}

	// Update user
	@GetMapping("/edit/{id}")
	public String editBlog(@PathVariable("id") Long id, Model model) {
		Blog blog = blogService.getBlogById(id);

		model.addAttribute("blog", blog);
		return "/apps/blogs/edit";
	}

	@PostMapping("/edit/{id}")
	public String updateUserInfo(@RequestParam("picture") MultipartFile file, Blog blog,
			RedirectAttributes redirectAttributes, Model model) {
		if (!file.isEmpty()) {
			String fileName = AppHelper.encode(blog.getTitle());
			blog.setPhoto(fileName);
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

		blogService.updateBlog(blog);

		redirectAttributes.addFlashAttribute("message", "The blog has been updated successfully.");

		return "redirect:/blogs/page/1";
	}
	
}
