package com.medicalcenterportal.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.medicalcenter.dto.MedicalAndMedicineRecordDTO;
import com.medicalcenter.entities.Medicine;
import com.medicalcenter.entities.Role;
import com.medicalcenter.entities.User;
import com.medicalcenter.exceptions.UserNotFoundException;
import com.medicalcenter.paging.PagingAndSortingHelper;
import com.medicalcenter.paging.PagingAndSortingParam;
import com.medicalcenter.services.MedicineRecordService;
import com.medicalcenter.services.MedicineService;
import com.medicalcenter.services.RoleService;
import com.medicalcenter.services.UserService;
import com.medicalcenterportal.helpers.AppConstant;
import com.medicalcenterportal.security.AppUserDetails;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/medicines")
public class MedicineController {
	
	private String defaultRedirectURL = "redirect:/medicines/page/1?sortField=patients&sortDir=asc";

	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
    @Autowired
    private MedicineService medicineService;
    @Autowired
	private MedicineRecordService medicineRecordService;

    @GetMapping("")
    public String showMedicineList(Model model,
                                   @RequestParam(name = "searchText", required = false) String searchText,
                                   @RequestParam(name = "sortField", defaultValue = "form") String sortField,
                                   @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir) {
        List<Medicine> medicines = medicineService.searchMedicines(searchText, sortField, sortDir);

        model.addAttribute("medicines", medicines);

		model.addAttribute("currentPage", "1");
		model.addAttribute("sortField", "genericName");
		model.addAttribute("sortDir", "asc");
		model.addAttribute("reverseSortDir", "desc");
		model.addAttribute("searchText", "");
		model.addAttribute("moduleURL", "/medicines");

        return "/apps/medicines/list";
    }
    
    
	@GetMapping("/page")
	public String listByPage() {
		return defaultRedirectURL;
	}

	@GetMapping("/page/{pageNum}")
	public String listByPage(
			@PagingAndSortingParam(listName = "medicines", moduleURL = "/medicines", defaultSortField = "genericName") PagingAndSortingHelper helper,
			@PathVariable(name = "pageNum") int pageNum,
			@RequestParam(name = "sortField", defaultValue = "genericName", required = false) String sortField,
			@RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir) {

		System.out
				.println("MedicineControllerlistByPage::" + pageNum + " sortField: " + sortField + " sortDir: " + sortDir);
		medicineService.listMedicinesByPage(pageNum, AppConstant.pageCount, helper);

		return "/apps/medicines/list";
	}
    
    
	
	@GetMapping("/listm")
	public String MedicineInfo(@AuthenticationPrincipal AppUserDetails loggedUser, Model model) throws UserNotFoundException {
	    List<Role> listRoles = roleService.getAllURoles();
	    
	    User user = userService.get(loggedUser.getId());
	    model.addAttribute("user", user);
	    model.addAttribute("listRoles", listRoles);
	    
	   
	    Long userId = loggedUser.getId();
	    
	    List<MedicalAndMedicineRecordDTO> records = medicineRecordService.getAllMedicalAndMedicineRecordsByDoctorId(userId);
	    
	    model.addAttribute("records", records);
	    
	    return "/profile/listm";
	}
	
	@GetMapping("/viewdetails/{id}")
	public String viewDetails(@PathVariable("id") Long id,@AuthenticationPrincipal AppUserDetails loggedUser, Model model) throws UserNotFoundException {
	    if (id == null) {
	        return "redirect:/error"; 
	    } else {
	        MedicalAndMedicineRecordDTO record = medicineRecordService.getMedicalAndMedicineRecordById(id);
	        if (record == null) {
	            return "redirect:/notfound";
	        }
	        List<Role> listRoles = roleService.getAllURoles();
		    
		    User user = userService.get(loggedUser.getId());
		    model.addAttribute("user", user);
		    model.addAttribute("listRoles", listRoles);
		    
		   
		    Long userId = loggedUser.getId();
	        List<MedicalAndMedicineRecordDTO> records = medicineRecordService.getAllMedicalAndMedicineRecordsByDoctorId(userId);
		    
	        model.addAttribute("record", record);
	        System.out.println("id+:"+ record.getId()+ "description" + record.getId());
	        return "/profile/viewdetails";
	    }
	}
	
	@GetMapping("/export-pdf/{id}")
	public void exportPDF(@PathVariable("id") Long id, HttpServletResponse response) {
	    try {
	        response.setContentType("application/pdf");
	        response.setHeader("Content-Disposition", "attachment; filename=medicalrecord.pdf");

	        MedicalAndMedicineRecordDTO record = medicineRecordService.getMedicalAndMedicineRecordById(id);

	        Document document = new Document();
	        PdfWriter.getInstance(document, response.getOutputStream());
	        document.open();

	        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
	        Paragraph title = new Paragraph("General Prescription", titleFont);
	        title.setAlignment(Element.ALIGN_CENTER);
	        document.add(title);
	        document.add(Chunk.NEWLINE);

	        Font labelFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
	        Font valueFont = new Font(Font.FontFamily.HELVETICA, 12);
	        
	        document.add(new Paragraph("Patient: " + record.getPatientName(), labelFont));
	        document.add(new Paragraph("Doctor: " + record.getDoctorName(), labelFont));
	        document.add(new Paragraph("Diagnostic: " + record.getDescription(), labelFont));
	        document.add(new Paragraph("LastVisitDate: " + record.getLastVisitDate(), labelFont));
	        document.add(new Paragraph("FollowUpDate: " + record.getFollowUpDate(), labelFont));

	        document.add(Chunk.NEWLINE);

	        PdfPTable table = new PdfPTable(4);
	        table.setWidthPercentage(100);
	        table.setWidths(new float[] { 2, 4, 2, 4}); 

	        PdfPCell cell = new PdfPCell(new Phrase("Medicine", labelFont));
	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        table.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase("Form", labelFont));
	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        table.addCell(cell);

	     
	        cell = new PdfPCell(new Phrase("Quantity", labelFont));
	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        table.addCell(cell);

	        cell = new PdfPCell(new Phrase("Note", labelFont));
	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        table.addCell(cell);

	        // Dữ liệu thuốc
	        table.addCell(new Phrase(record.getMedicineName(), valueFont));
	        table.addCell(new Phrase(record.getQuantity(), valueFont));
	        table.addCell(new Phrase(record.getNote(), valueFont));
	        table.addCell(new Phrase(record.getForm(), valueFont));


	        document.add(table);

	        document.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


    @GetMapping("/add")
    public String showCreateMedicineForm(Model model) {
        model.addAttribute("medicine", new Medicine());
        return "/apps/medicines/add";
    }

    @PostMapping("/add")
    public String createMedicine(@ModelAttribute Medicine medicine) {
    	Long id = medicine.getId();
    	int _version = medicine.get_version();
    	String genericName = medicine.getGenericName();
    	String brandName = medicine.getBrandName();
    	String measurementUnit = medicine.getMeasurementUnit();
    	String form = medicine.getForm();
    	
    	
        medicineService.createMedicine(id,_version, genericName,brandName,measurementUnit,form);
        return "redirect:/medicines";
    }

    @GetMapping("/details/{id}")
    public String showMedicineDetails(@PathVariable(name = "id") Long id, Model model) {
        Medicine medicine = medicineService.getMedicineById(id);
        model.addAttribute("medicine", medicine);
        return "/apps/medicines/details";
    }

    @GetMapping("/edit/{id}")
    public String showEditMedicineForm(@PathVariable(name = "id") Long id, Model model) {
        Medicine medicine = medicineService.getMedicineById(id);
        model.addAttribute("medicine", medicine);
        return "/apps/medicines/edit";
    }

    @PostMapping("/edit/{id}")
    public String editMedicalRecord(@PathVariable("id") Long id, @ModelAttribute("medicine") Medicine updatedMedicine) throws NotFoundException {
        if (updatedMedicine != null) {
        	int _version = updatedMedicine.get_version();
            String genericName = updatedMedicine.getGenericName();
            String brandName = updatedMedicine.getBrandName();
            String measurementUnit = updatedMedicine.getMeasurementUnit();
            String form = updatedMedicine.getForm();
            
            medicineService.updateMedicine(id, _version, genericName, brandName, measurementUnit, form);
        }
        return "redirect:/medicines";
    }

    @GetMapping("/delete/{id}")
	public String deleteMedicine(@PathVariable("id") Long id) {
		medicineService.deleteMedicine(id);
		return "redirect:/medicines";
	}
}