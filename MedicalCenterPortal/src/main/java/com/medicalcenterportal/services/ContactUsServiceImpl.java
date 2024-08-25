package com.medicalcenterportal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicalcenter.entities.Contact;
import com.medicalcenterportal.repository.ContactUsRepository;

@Service
public class ContactUsServiceImpl implements ContactUsService {

	@Autowired
	ContactUsRepository contactUsRepository;
	
	@Override
	public Contact saveContact(Contact contact) {
	return contactUsRepository.save(contact);
	}

	@Override
	public List<Contact> getAllContacts() {
		// TODO Auto-generated method stub
		return contactUsRepository.findAll();
	}

	@Override
	public Contact findById(Long id) {
	    return contactUsRepository.findById(id).orElse(null);
	}

	@Override
	public boolean updateEmailSent(Long contactId, boolean emailSent) {
		  Optional<Contact> optionalContact = contactUsRepository.findById(contactId);
	        if (optionalContact.isPresent()) {
	            Contact contact = optionalContact.get();
	            contact.setEmailSent(emailSent);
	            contactUsRepository.save(contact);
	            return true;
	        }
	        return false;
	}


}
