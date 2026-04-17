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
public class SampleDataInitializer implements CommandLineRunner {

    @Autowired
    private EnquiryRepo enquiryRepo;
    
    @Autowired
    private CourseRepo courseRepo;

    @Override
    public void run(String... args) throws Exception {
        // Check if enquiries already exist
        if (enquiryRepo.count() == 0) {
            // Get available courses
            List<Course> courses = courseRepo.findAll();
            if (courses.isEmpty()) {
                System.out.println("No courses found. Skipping sample enquiry creation.");
                return;
            }
            
            Random random = new Random();
            String[] statuses = {"New", "Enrolled", "Lost"};
            String[] classModes = {"Online", "Classroom", "Hybrid"};
            String[] studentNames = {"John Doe", "Jane Smith", "Mike Johnson", "Sarah Williams", "David Brown", 
                                  "Emily Davis", "Chris Wilson", "Lisa Anderson", "Tom Martinez", "Amy Taylor"};
            String[] phoneNumbers = {"9876543210", "9876543211", "9876543212", "9876543213", "9876543214",
                                  "9876543215", "9876543216", "9876543217", "9876543218", "9876543219"};
            
            // Create sample enquiries
            for (int i = 0; i < 10; i++) {
                Enquiry enquiry = Enquiry.builder()
                    .stuName(studentNames[i])
                    .stuPhno(phoneNumbers[i])
                    .courseId(courses.get(random.nextInt(courses.size())).getCourseId())
                    .classMode(classModes[random.nextInt(classModes.length)])
                    .enqStatus(statuses[random.nextInt(statuses.length)])
                    .counsellorId(1) // Assuming counsellor ID 1 exists
                    .build();
                
                enquiryRepo.save(enquiry);
            }
            
            System.out.println("Successfully created 10 sample enquiries!");
        } else {
            System.out.println("Sample enquiries already exist. Count: " + enquiryRepo.count());
        }
    }
}
