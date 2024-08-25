package com.medicalcenter.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.medicalcenter.entities.User;

@Repository
public interface UserRepository extends SearchRepository<User, Long> {
	@Query("SELECT u FROM User u WHERE u.email = :email")
	public User getUserByEmail(@Param("email") String email);
	
	@Query("SELECT u FROM User u JOIN u.roles r WHERE r.id = :roleId")
    List<User> findByRoleId(@Param("roleId") Long roleId);

	//@Query("SELECT u FROM User u WHERE CONCAT(u.id, ' ', u.email, ' ', u.firstName, ' ', u.lastName) LIKE %?1%")
	@Query("SELECT u FROM User u JOIN u.roles r WHERE CONCAT(u.id, ' ', u.email, ' ', u.firstName, ' ', u.lastName, ' ', r.name) LIKE %?1%")
	public Page<User> findAll(@Param("searchText") String searchText, Pageable pageable);

	@Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
	@Modifying
	public void updateEnabledStatus(Long id, boolean enabled);

	public Long countById(Long id);

	public User findByEmail(String email);

	public User findByResetPasswordToken(String email);
	
	public Optional<User> findById(UserRepository userRepo);
	
	 @Query("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE %:keyword% OR LOWER(u.lastName) LIKE %:keyword%")
	    List<User> findByFirstNameOrLastNameContainingIgnoreCase(@Param("keyword") String keyword);
	   List<User> findBySpecialtyIsNotNull();
}