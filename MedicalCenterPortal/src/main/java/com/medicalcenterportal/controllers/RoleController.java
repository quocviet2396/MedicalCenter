package com.medicalcenterportal.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.medicalcenter.entities.Role;
import com.medicalcenter.paging.PagingAndSortingHelper;
import com.medicalcenter.paging.PagingAndSortingParam;
import com.medicalcenter.services.RoleService;
import com.medicalcenterportal.helpers.AppConstant;

@Controller
@RequestMapping("/roles")
public class RoleController {

	private String defaultRedirectURL = "redirect:/roles/page/1?searchText=";
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping("")
	public String getRoleList(Model model) {
		List<Role> listRoles = roleService.getAllURoles();

		model.addAttribute("listRoles", listRoles);

		model.addAttribute("currentPage", "1");
		model.addAttribute("searchText", "");
		model.addAttribute("moduleURL", "/roles");

		return "/apps/roles/list";
	}

	@GetMapping("/page")
	public String listByPage() {
		return defaultRedirectURL;
	}

	@GetMapping("/page/{pageNum}")
	public String listByPage(
			@PagingAndSortingParam(listName = "listRoles", moduleURL = "/roles", defaultSortField = "id") PagingAndSortingHelper helper,
			@PathVariable(name = "pageNum") int pageNum) {

		roleService.listRoleByPage(pageNum, AppConstant.pageCount, helper);

		return "/apps/roles/list";
	}

	
	
	
	@GetMapping("/add")
    public String showAddRoleForm(Model model) {
        model.addAttribute("role", new Role());
        return "/apps/roles/add";
    }

    @PostMapping("/add")
    public String addRole(Role role) {
        roleService.createRole(role);
        return "redirect:/roles";
    }

    @GetMapping("/edit/{id}")
    public String showEditRoleForm(@PathVariable("id") Long id, Model model) {
        Role role = roleService.getRoleById(id);
        if (role == null) {
            // Handle the case when the role is not found
            return defaultRedirectURL;
        }
        model.addAttribute("role", role);
        return "/apps/roles/edit";
    }

    @PostMapping("/edit/{id}")
    public String editRole(Role role, RedirectAttributes redirectAttributes, Model model) {
        roleService.updateRole(role);
        redirectAttributes.addFlashAttribute("message", "The role has been saved successfully.");
        return "redirect:/roles";
    }

    @GetMapping("/delete/{id}")
    public String deleteRole(@PathVariable("id") Long id) {
        roleService.deleteRole(id);
        return "redirect:/roles";
    }
}
