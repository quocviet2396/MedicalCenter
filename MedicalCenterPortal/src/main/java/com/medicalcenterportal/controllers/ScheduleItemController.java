package com.medicalcenterportal.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.medicalcenter.entities.ScheduleItem;
import com.medicalcenter.entities.Specialty;
import com.medicalcenter.entities.User;
import com.medicalcenter.repositories.UserRepository;
import com.medicalcenter.services.SpecialtyService;
import com.medicalcenter.services.UserService;
import com.medicalcenterportal.adapter.ScheduleItemTypeAdapter;
import com.medicalcenterportal.repository.ScheduleItemRepository;
import com.medicalcenterportal.security.AppUserDetails;
import com.medicalcenterportal.services.ScheduleItemService;



@Controller 
@RequestMapping("/scheduleofdoctor")
public class ScheduleItemController {
	
	@Autowired
    private ScheduleItemService scheduleItemService;
	@Autowired 
	private ScheduleItemRepository scheduleItemRepository;
	@Autowired 
	private UserRepository userRepository;
	@Autowired 
	private SpecialtyService specialtyService;
	@Autowired
	UserService userService;
	@GetMapping("/list")
	public String showScheduleList(Model model, Principal principal) {
	    // Kiểm tra vai trò của người dùng đăng nhập
	    boolean isAdmin = false; // Giả sử mặc định là người dùng không phải là ADMIN
	    String username = principal.getName();

	    // Kiểm tra vai trò của người dùng
	    for (GrantedAuthority authority : ((Authentication) principal).getAuthorities()) {
	        if (authority.getAuthority().equals("ADMIN")) { // Lưu ý: Vai trò ADMIN phải có tiền tố "ROLE_"
	            isAdmin = true;
	            break;
	        }
	    }

	    List<ScheduleItem> scheduleItems;
	    if (isAdmin) {
	        // Nếu người dùng là admin, lấy tất cả các mục lịch làm việc
	        scheduleItems = scheduleItemService.getAllScheduleItems();
	    } else {
	        // Nếu người dùng không phải là admin, lấy lịch làm việc của bác sĩ đó
	        User doctor = userRepository.getUserByEmail(username);
	        scheduleItems = scheduleItemService.getScheduleItemsByDoctor(doctor);
	    }

	    model.addAttribute("scheduleItems", scheduleItems);
	    return "apps/scheduleofdoctor/schedule_list"; // Trả về tên của view
	}



	@GetMapping("/add")
	public String showScheduleForm(Model model) {
        model.addAttribute("scheduleItem", new ScheduleItem()); // Thêm đối tượng scheduleItem vào model
        // Logic để lấy danh sách bác sĩ và các dữ liệu khác cần thiết và thêm vào model nếu cần
        List<User> doctors = scheduleItemRepository.findAllByDoctorRoleName("DOCTOR");
        model.addAttribute("doctors", doctors);
       
        return "/apps/scheduleofdoctor/scheduleItemForm"; // Trả về tên của view
    }


	@PostMapping("/save")
	public String saveScheduleItem(@ModelAttribute("scheduleItem") ScheduleItem scheduleItem, Model model) {
	    try {
	        // Logic để lưu hoặc cập nhật mục lịch làm việc
	        scheduleItemService.saveScheduleItem(scheduleItem);
	        return "redirect:/scheduleofdoctor/list";
	    } catch (RuntimeException e) {
	        // Nếu gặp lỗi, thêm thông báo lỗi vào model
	        model.addAttribute("errorMessage", e.getMessage());
	        return "/apps/scheduleofdoctor/scheduleItemForm"; // Chuyển hướng đến trang view với thông báo lỗi
	    }
	}

    @GetMapping("/edit/{id}")
    public String showEditScheduleForm(@PathVariable("id") Long id, Model model) {
        // Lấy thông tin scheduleItem từ ID
        ScheduleItem scheduleItem = scheduleItemService.getScheduleItemById(id);
               
        model.addAttribute("scheduleItem", scheduleItem);

        // Lấy danh sách bác sĩ và các dữ liệu khác cần thiết và thêm vào model nếu cần
        List<User> doctors = scheduleItemRepository.findAllByDoctorRoleName("DOCTOR");
        model.addAttribute("doctors", doctors);

        return "editScheduleItem"; // Trả về tên của view
    }

    @PostMapping("/update/{id}")
    public String updateScheduleItem(@PathVariable("id") Long id, @ModelAttribute("scheduleItem") ScheduleItem scheduleItem) {
        // Logic để cập nhật mục lịch làm việc
        scheduleItem.setId(id); // Set ID cho scheduleItem
        scheduleItemService.updateScheduleItem(scheduleItem);
        return "redirect:/scheduleofdoctor/list";
    }
    @GetMapping("/delete/{id}")
    public String deleteScheduleItem(@PathVariable("id") Long id) {
        // Xóa mục lịch làm việc dựa trên ID
    	scheduleItemRepository.deleteById(id);
        return "redirect:/scheduleofdoctor/list";
    }
    @GetMapping("/calendar")
    public String showCalendar(Model model, Principal principal) {
        String username = principal.getName(); // Lấy tên người dùng đăng nhập
        User user = userRepository.findByEmail(username);

        boolean isDoctor = false; // Giả sử mặc định là người dùng không phải là bác sĩ

        // Kiểm tra vai trò của người dùng
        for (GrantedAuthority authority : ((Authentication) principal).getAuthorities()) {
            if (authority.getAuthority().equals("DOCTOR")) { // Kiểm tra xem người dùng có vai trò là bác sĩ không
                isDoctor = true;
                break;
            }
        }

        List<ScheduleItem> scheduleItems;
        if (isDoctor) {
            // Nếu người dùng là bác sĩ, lấy danh sách lịch làm việc của bác sĩ đó
            scheduleItems = scheduleItemRepository.findByDoctor(user);
        } else {
            // Nếu người dùng không phải là bác sĩ, trả về lỗi 404
            return "error/404";
        }

        if (scheduleItems.isEmpty()) {
            // Nếu danh sách lịch làm việc trống, có thể xử lý tùy thuộc vào yêu cầu của ứng dụng
            // Ví dụ: Trả về một trang thông báo không có lịch làm việc nào
            return "error/no_schedule";
        }

        // Serialize danh sách ScheduleItem thành chuỗi JSON
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ScheduleItem.class, new ScheduleItemTypeAdapter())
                .create();
        String scheduleItemsJson = gson.toJson(scheduleItems);

        // Thêm danh sách JSON vào model để sử dụng trong view
        model.addAttribute("scheduleItemsJson", scheduleItemsJson);
        return "/apps/scheduleofdoctor/calendar"; // Trả về trang chứa lịch
    }
    @GetMapping("/doctors")
    public String showDoctorList(Model model) {
        // Lấy danh sách các bác sĩ từ DoctorService
        List<User> doctors = userService.getDoctors();
        
        // Truyền danh sách bác sĩ vào model để hiển thị trong view
        model.addAttribute("doctors", doctors);
        
        // Trả về tên của view để hiển thị danh sách bác sĩ
        return "doctor-list"; // Thay "doctor-list" bằng tên của view của bạn
    }
    @GetMapping("/schedule-items")
    public String showScheduleItemList(@RequestParam(name = "workdateString", required = false) String workdateString,
                                       @RequestParam(name = "specialty", required = false) String specialty,
                                       Model model, @AuthenticationPrincipal AppUserDetails userDetails) {
    	if (userDetails != null) {
			model.addAttribute("isLoggedIn", true);
		} else {
			model.addAttribute("isLoggedIn", false);
		}
    	   List<Specialty> specialties = specialtyService.getAllSpecialty();
        LocalDate workdate = null;
        if (workdateString != null && !workdateString.isEmpty()) {
            workdate = LocalDate.parse(workdateString);
        } else {
            // Lấy ngày hiện tại nếu workdateString là null hoặc rỗng
            workdate = LocalDate.now();
        }

        List<ScheduleItem> scheduleItems;
        

        // Kiểm tra xem specialty có được chỉ định không
        if (specialty != null && !specialty.isEmpty()) {
            // Nếu specialty được chỉ định, lấy danh sách ScheduleItem dựa trên cả workdate và specialty
            scheduleItems = scheduleItemService.getScheduleItemsByWorkdateAndSpecialty(workdate, specialty);
        }
        else {
            // Nếu không có specialty được chỉ định, chỉ lấy dựa trên workdate
            scheduleItems = scheduleItemService.getScheduleItemsByWorkdate(workdate);
        }
			
		
        
        // Truyền danh sách ScheduleItem vào model
        model.addAttribute("scheduleItems", scheduleItems);
        model.addAttribute("specialties", specialties);
        
        // Trả về tên view để hiển thị danh sách ScheduleItem
        return "schedule-item-list"; // Thay "schedule-item-list" bằng tên view của bạn
    }



}


