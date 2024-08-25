package com.medicalcenterportal.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.medicalcenter.entities.Address;
import com.medicalcenter.entities.Role;
import com.medicalcenter.entities.User;
import com.medicalcenter.enums.Gender;

public class AppUserDetails implements UserDetails {

	private static final long serialVersionUID = 1791699434224222474L;

	private User user;

	public AppUserDetails(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Role> roles = user.getRoles();

		List<SimpleGrantedAuthority> authories = new ArrayList<>();

		for (Role role : roles) {
			authories.add(new SimpleGrantedAuthority(role.getName()));
		}

		return authories;
	}

	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.isEnabled();
	}
	
	//Addition methods
	public String roles() {
		return user.getRoles().toString();
	}
	
	public Set<Role> getRoles() {
		return user.getRoles();
	}
	
	public boolean isChangePassword() {
		return user.isChangePassword();
	}
	
	public Long getId() {
		return user.getId();
	}
	
	public String getEmail() {
		return user.getEmail();
	}
	
	public String getPhoto() {
		return user.getPhoto();
	}
	
	public String getFullname() {
		return this.user.getFirstName() + " " + this.user.getLastName();
	}
	
	public String getFirstName() {
		return this.user.getFirstName();
	}
	
	public void setFirstName(String firstName) {
		this.user.setFirstName(firstName);
	}

	public String getLastName() {
		return this.user.getLastName();
	}
	
	public void setLastName(String lastName) {
		this.user.setLastName(lastName);
	}	
	
	public boolean hasRole(String roleName) {
		return user.hasRole(roleName);
	}
	
	
	// Getter and Setter for new properties
	public String getAddress() {
	    return user.getAddress().getAddress();
	}

	public void setAddress(String address) {
	    user.getAddress().setAddress(address);
	}

	public String getPhone() {
	    return user.getAddress().getPhone();
	}

	public void setPhone(String phone) {
	    user.getAddress().setPhone(phone);
	}

	public String getState() {
	    return user.getAddress().getState();
	}

	public void setState(String state) {
	    user.getAddress().setState(state);
	}

	public String getCountry() {
	    return user.getAddress().getCountry();
	}

	public void setCountry(String country) {
	    user.getAddress().setCountry(country);
	}

	public String getPostalCode() {
	    return user.getAddress().getPostalCode();
	}

	public void setPostalCode(String postalCode) {
	    user.getAddress().setPostalCode(postalCode);
	}

    public String getDateOfBirth() {
        return user.getDateOfBirth();
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.user.setDateOfBirth(dateOfBirth);
    }

    public String getGender() {
        return user.getGender().toString(); // Chuyển đổi enum thành String
    }

    public void setGender(String gender) {
        Gender enumGender = Gender.valueOf(gender); // Chuyển đổi String thành enum
        user.setGender(enumGender);
    }
    
    // Getter for user
    public User getUser() {
        return user;
    }
	
}
