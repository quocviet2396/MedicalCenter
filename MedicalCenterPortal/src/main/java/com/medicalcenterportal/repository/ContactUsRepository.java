package com.medicalcenterportal.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.medicalcenter.entities.Contact;

public interface ContactUsRepository extends  JpaRepository<Contact, Long>{

}
