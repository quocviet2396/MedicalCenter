package com.medicalcenterportal.services;

import java.util.List;

import com.medicalcenter.entities.Contact;

public interface ContactUsService {
	Contact saveContact(Contact contact);

	List<Contact> getAllContacts();

	Contact findById(Long id);
	 boolean updateEmailSent(Long contactId, boolean emailSent);
}
