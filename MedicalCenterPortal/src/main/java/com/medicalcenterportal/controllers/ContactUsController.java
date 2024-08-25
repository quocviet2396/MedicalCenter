package com.medicalcenterportal.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.medicalcenter.entities.Contact;
import com.medicalcenterportal.security.AppUserDetails;
import com.medicalcenterportal.services.ContactUsService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/contactus")
public class ContactUsController {
	@Autowired
	ContactUsService  contactService;
	@Autowired
    private JavaMailSender emailSender;
	@GetMapping("/sendEmailForm/{id}")
	public String showEmailForm(@PathVariable("id") Long contactId, Model model) {
	    Contact contact = contactService.findById(contactId);
	    model.addAttribute("contact", contact);
	    return "contact/emailForm"; // Sửa đường dẫn view
	}

	@PostMapping("/sendEmail")
	public String sendEmail(@RequestParam("contactId") Long contactId,
	                        @RequestParam("subject") String subject,
	                        @RequestParam("body") String body) {
	    Optional<Contact> contactOptional = Optional.of(contactService.findById(contactId));
	    if (contactOptional.isPresent()) {
	        Contact contact = contactOptional.get();
	        String toEmail = contact.getEmail();
	        subject = contact.getTitle();
	        MimeMessage message = emailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message);

	        try {
	            helper.setTo(toEmail);
	            helper.setSubject(subject);
	            helper.setText(body);
	            emailSender.send(message);
	            return "redirect:/contactus/list"; // Chuyển hướng sau khi gửi email thành công
	        } catch (MessagingException e) {
	            e.printStackTrace();
	            return "Failed to send email!";
	        }
	    } else {
	        return "Contact not found!";
	    }
	}



	
	 @GetMapping("/list")
	    public String showContactList(Model model) {
	        // Gọi service để lấy danh sách contact từ cơ sở dữ liệu
	        List<Contact> contactList = contactService.getAllContacts();

	        // Thêm danh sách contact vào model để hiển thị trên view
	        model.addAttribute("contacts", contactList);

	        // Trả về view hiển thị danh sách contact
	        return "contact/listContactUs"; // Giả sử view có tên là list.html và được đặt trong thư mục contact
	    }
	
	@GetMapping("/new")
	public String showContactForm(Model model, @AuthenticationPrincipal AppUserDetails userDetails) {
		if (userDetails != null) {
			model.addAttribute("isLoggedIn", true);
		} else {
			model.addAttribute("isLoggedIn", false);
		}
	    model.addAttribute("contact", new Contact());
	    return "/contact/contact"; // Trả về view contact.html
	}
	@PostMapping("/save")
	public String CreateContactUs(@ModelAttribute Contact contact) {
		//TODO: process POST request
		contactService.saveContact(contact);
		return "/apps/appointmentNoLogin/dangkysuccess";
	}
	@GetMapping("/toggleEmailSent/{id}/{emailSent}")
    public String toggleEmailSent(@PathVariable("id") Long contactId, 
                                  @PathVariable("emailSent") boolean emailSent,
                                  Model model) {
        // Lấy contact từ cơ sở dữ liệu
        Contact contact = contactService.findById(contactId);
        
        // Cập nhật trạng thái emailSent của contact
        contact.setEmailSent(emailSent);
        
        // Lưu contact đã cập nhật vào cơ sở dữ liệu
        contactService.updateEmailSent(contactId, emailSent);
        
        // Lấy danh sách contact mới sau khi cập nhật
        List<Contact> contactList = contactService.getAllContacts();
        	
        // Thêm danh sách contact vào model để hiển thị trên view
        model.addAttribute("contacts", contactList);
        
        // Trả về view hiển thị danh sách contact
        return "contact/listContactUs";
    }
}
