package com.ashok.it.counsellorsportal.Config;

import com.ashok.it.counsellorsportal.Model.Enquiry;
import com.ashok.it.counsellorsportal.Repository.EnquiryRepo;
import com.ashok.it.counsellorsportal.Repository.CourseRepo;
import com.ashok.it.counsellorsportal.Model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class DebugSampleData implements CommandLineRunner {

    @Autowired
    private EnquiryRepo enquiryRepo;
    
    @Autowired
    private CourseRepo courseRepo;

    @Override
    public void run(String... args) throws Exception {
        // Check existing enquiries and their course IDs
        List<Enquiry> enquiries = enquiryRepo.findAll();
        List<Course> courses = courseRepo.findAll();
        
        System.out.println("=== EXISTING ENQUIRIES ===");
        for (Enquiry enq : enquiries) {
            System.out.println("Enquiry ID: " + enq.getEnqId() + 
                             ", Student: " + enq.getStuName() + 
                             ", Course ID: " + enq.getCourseId() + 
                             ", Status: " + enq.getEnqStatus() + 
                             ", Class Mode: " + enq.getClassMode());
        }
        
        System.out.println("\n=== FIRST 5 COURSES ===");
        for (int i = 0; i < Math.min(5, courses.size()); i++) {
            Course course = courses.get(i);
            System.out.println("Course ID: " + course.getCourseId() + ", Course Name: " + course.getCourseName());
        }
        
        // Create enquiries for first 3 courses if they don't exist
        createEnquiriesForFirstCourses(courses);
    }
    
    private void createEnquiriesForFirstCourses(List<Course> courses) {
        if (courses.size() >= 3) {
            String[] studentNames = {"Alice Johnson", "Bob Smith", "Charlie Brown"};
            String[] phoneNumbers = {"9876543210", "9876543211", "9876543212"};
            String[] statuses = {"New", "Enrolled", "Lost"};
            String[] classModes = {"Online", "Classroom", "Hybrid"};
            
            for (int i = 0; i < 3; i++) {
                Course course = courses.get(i);
                
                // Check if enquiry already exists for this course
                List<Enquiry> existingEnqs = enquiryRepo.findByCounsellorIdAndCourseId(1, course.getCourseId());
                if (existingEnqs.isEmpty()) {
                    Enquiry enquiry = Enquiry.builder()
                        .stuName(studentNames[i])
                        .stuPhno(phoneNumbers[i])
                        .courseId(course.getCourseId())
                        .classMode(classModes[i])
                        .enqStatus(statuses[i])
                        .counsellorId(1)
                        .build();
                    
                    enquiryRepo.save(enquiry);
                    System.out.println("Created enquiry for course: " + course.getCourseName() + " (ID: " + course.getCourseId() + ")");
                } else {
                    System.out.println("Enquiry already exists for course: " + course.getCourseName() + " (ID: " + course.getCourseId() + ")");
                }
            }
        }
    }
}
