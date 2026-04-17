package com.ashok.it.counsellorsportal.Controller;

import com.ashok.it.counsellorsportal.Model.Enquiry;
import com.ashok.it.counsellorsportal.Model.Course;
import com.ashok.it.counsellorsportal.Services.EnqService;
import com.ashok.it.counsellorsportal.Services.CourseService;
import com.ashok.it.counsellorsportal.Dto.EnqFilterRequestDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EnquiryController {

    @Autowired
    private EnqService enqService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/add-enq")
    public String addEnquiry(Model model, HttpSession session) {
        Integer counsellorId = (Integer) session.getAttribute("counsellorId");
        if (counsellorId == null) {
            return "redirect:/";
        }
        
        List<Course> courses = courseService.getCourses();
        System.out.println("Retrieved " + courses.size() + " courses for add-enquiry page");
        for (Course course : courses) {
            System.out.println("Course: " + course.getCourseId() + " - " + course.getCourseName());
        }
        model.addAttribute("courses", courses);
        model.addAttribute("enq", new Enquiry());
        return "add-enq";
    }

    @PostMapping("/add-enq")
    public String handleAddEnquiry(@ModelAttribute("enq") Enquiry enq, HttpSession session, Model model) {
        Integer counsellorId = (Integer) session.getAttribute("counsellorId");
        if (counsellorId == null) {
            return "redirect:/";
        }
        
        boolean status = enqService.addEnquiry(enq, counsellorId);
        if (status) {
            model.addAttribute("smsg", "Enquiry Added Successfully");
        } else {
            model.addAttribute("emsg", "Failed to Add Enquiry");
        }
        
        List<Course> courses = courseService.getCourses();
        model.addAttribute("courses", courses);
        return "add-enq";
    }

    @GetMapping("/view-enqs")
    public String viewEnquiries(Model model, HttpSession session) {
        Integer counsellorId = (Integer) session.getAttribute("counsellorId");
        if (counsellorId == null) {
            return "redirect:/";
        }
        
        List<Enquiry> enquiries = enqService.getAllEnquiries(counsellorId);
        List<Course> courses = courseService.getCourses();
        
        model.addAttribute("enquiries", enquiries);
        model.addAttribute("courses", courses);
        model.addAttribute("filterDto", new EnqFilterRequestDto());
        return "view-enqs";
    }

    @GetMapping("/filter-enqs")
    public String filterEnquiries(@ModelAttribute("filterDto") EnqFilterRequestDto filterDto, 
                                 Model model, HttpSession session) {
        Integer counsellorId = (Integer) session.getAttribute("counsellorId");
        if (counsellorId == null) {
            return "redirect:/";
        }
        
        // Debug logging
        System.out.println("Filter Parameters:");
        System.out.println("Counsellor ID from session: " + counsellorId);
        System.out.println("Course ID: " + filterDto.getCourseId());
        System.out.println("Enq Status: '" + filterDto.getEnqStatus() + "'");
        System.out.println("Class Mode: '" + filterDto.getClassMode() + "'");
        
        List<Enquiry> enquiries = enqService.getEnquiriesWithFilters(filterDto, counsellorId);
        List<Course> courses = courseService.getCourses();
        
        System.out.println("Filtered enquiries count: " + enquiries.size());
        
        model.addAttribute("enquiries", enquiries);
        model.addAttribute("courses", courses);
        model.addAttribute("filterDto", filterDto);
        return "view-enqs";
    }

    @GetMapping("/edit-enq/{enqId}")
    public String editEnquiry(@PathVariable Integer enqId, Model model, HttpSession session) {
        Integer counsellorId = (Integer) session.getAttribute("counsellorId");
        if (counsellorId == null) {
            return "redirect:/";
        }
        
        Enquiry enquiry = enqService.editEnquiry(enqId);
        if (enquiry == null || !enquiry.getCounsellorId().equals(counsellorId)) {
            return "redirect:/view-enqs";
        }
        
        List<Course> courses = courseService.getCourses();
        model.addAttribute("enq", enquiry);
        model.addAttribute("courses", courses);
        return "add-enq";
    }

    @PostMapping("/update-enq")
    public String updateEnquiry(@ModelAttribute("enq") Enquiry enq, HttpSession session, Model model) {
        Integer counsellorId = (Integer) session.getAttribute("counsellorId");
        if (counsellorId == null) {
            return "redirect:/";
        }
        
        boolean status = enqService.updateEnquiry(enq);
        if (status) {
            model.addAttribute("smsg", "Enquiry Updated Successfully");
        } else {
            model.addAttribute("emsg", "Failed to Update Enquiry");
        }
        
        List<Course> courses = courseService.getCourses();
        model.addAttribute("courses", courses);
        return "add-enq";
    }
}
