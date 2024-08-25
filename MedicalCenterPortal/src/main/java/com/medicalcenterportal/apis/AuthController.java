package com.medicalcenterportal.apis;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicalcenterportal.dtos.JwtResponse;
import com.medicalcenterportal.dtos.LoginDTO;
import com.medicalcenterportal.dtos.MessageResponse;
import com.medicalcenterportal.dtos.RegisterDTO;
import com.medicalcenterportal.security.AppUserDetails;
import com.medicalcenterportal.security.jwt.JwtUtils;
import com.medicalcenter.dto.UserDTO;
import com.medicalcenter.entities.Address;
import com.medicalcenter.entities.Role;
import com.medicalcenter.entities.User;
import com.medicalcenter.enums.Gender;
import com.medicalcenter.mappers.UserMapper;
import com.medicalcenter.services.RoleService;
import com.medicalcenter.services.UserService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/apis/v1")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDTO loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		// Initialize variables for user information
		String address = "";
		String phone = "";
		String state = "";
		String country = "";
		String postalCode = "";
		String gender = ""; // Initialize gender
		String dateOfBirth = "";

		// Check if user and address information are not null
		if (userDetails.getUser() != null) {
			// Access gender information if available
			Gender userGender = userDetails.getUser().getGender();
			// Check if gender is not null before calling toString()
			if (userGender != null) {
				gender = userGender.toString();
			}

			// Access date of birth information if available
			String userDateOfBirth = userDetails.getDateOfBirth();
			if (userDateOfBirth != null) {
				dateOfBirth = userDateOfBirth.toString();
			}

			// Access address information if available
			if (userDetails.getUser().getAddress() != null) {
				Address userAddress = userDetails.getUser().getAddress();
				address = userAddress.getAddress();
				phone = userAddress.getPhone();
				state = userAddress.getState();
				country = userAddress.getCountry();
				postalCode = userAddress.getPostalCode();
			}
		}

		// You can similarly check and assign other fields

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getFullname(),
				userDetails.getEmail(), userDetails.getFirstName(), userDetails.getLastName(), address, phone, state,
				country, postalCode, dateOfBirth, gender, roles, "success"));
		// new JwtResponse(jwt, userDetails.getId(), userDetails.getFullname(),
		// userDetails.getEmail(), roles, userDetails.get));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDTO registerDTO) {
		if (userService.isEmailExisted(registerDTO.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create a new UserDTO object
		UserDTO userDTO = new UserDTO(registerDTO.getEmail(), registerDTO.getPassword(), registerDTO.getFirstName(),
				registerDTO.getLastName(), null, null, null, true);

		// Map UserDTO to User entity
		User user = UserMapper.MAPPER.userDTOToUser(userDTO);

		userService.saveFlutter(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
