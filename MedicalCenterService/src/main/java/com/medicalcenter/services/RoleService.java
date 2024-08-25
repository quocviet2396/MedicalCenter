package com.medicalcenter.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicalcenter.entities.Role;
import com.medicalcenter.paging.PagingAndSortingHelper;
import com.medicalcenter.repositories.RoleRepository;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class RoleService {

	@Autowired
	private RoleRepository roleRepo;
	
	public void listRoleByPage(int pageNum, int pageCount, PagingAndSortingHelper helper) {
		helper.listEntities(pageNum, pageCount, roleRepo);
	}
	
	public List<Role> getAllURoles() {
		
		return (List<Role>) roleRepo.findAll();
	}
	
	
	public Role findByName(String name) {
        return roleRepo.findByName(name);
    }
	
	public void createSampleData() {
		Role role1 = new Role();
		role1.setName("ADMIN");
		role1.setDescription("ADMIN");

		Role role2 = new Role();
		role2.setName("DOCTOR");
		role2.setDescription("DOCTOR");

		Role role3 = new Role();
		role2.setName("PATIENT");
		role2.setDescription("PATIENT");

		roleRepo.save(role1);
		roleRepo.save(role2);
		roleRepo.save(role3);
	}
	
	
	public Role getRoleById(Long id) {
        return roleRepo.findById(id).orElse(null);
    }

    public Role createRole(Role role) {
    	role.setCreateOn(LocalDateTime.now());
        return roleRepo.save(role);
    }

    public Role updateRole(Role roleDto) {
    	Role roleInDB = roleRepo.findById(roleDto.getId()).get();
    	roleInDB.setName(roleDto.getName());
    	roleInDB.setDescription(roleDto.getDescription());
    	roleInDB.setUpdateOn(LocalDateTime.now());
        return roleRepo.save(roleInDB);
    }

    public void deleteRole(Long id) {
    	roleRepo.deleteById(id);
    }

    
    
    

}