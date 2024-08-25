package com.medicalcenterportal.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicalcenter.entities.Contact;
import com.medicalcenterportal.services.ContactUsService;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping({"/apis/v1/contactus", "/apis/test/contactus"})
public class ContactRestController {

	@Autowired
	ContactUsService  contactUsService;
	
	@PostMapping
	public Contact createContact(@RequestBody Contact contact) {
		return contactUsService.saveContact(contact);
	}
	
}
