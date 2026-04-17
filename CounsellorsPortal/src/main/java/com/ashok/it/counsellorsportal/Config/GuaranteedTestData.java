package com.ashok.it.counsellorsportal.Config;

import com.ashok.it.counsellorsportal.Model.Enquiry;
import com.ashok.it.counsellorsportal.Repository.EnquiryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class GuaranteedTestData implements CommandLineRunner {

    @Autowired
    private EnquiryRepo enquiryRepo;

    @Override
    public void run(String... args) throws Exception {
        // Create guaranteed matching enquiry for counsellorId = 1 and courseId = 1
        Enquiry existingEnq = enquiryRepo.findByCounsellorIdAndCourseId(1, 1)
            .stream()
            .findFirst()
            .orElse(null);
            
        if (existingEnq == null) {
            Enquiry guaranteedEnq = Enquiry.builder()
                .stuName("Test Student")
                .stuPhno("9876543210")
                .courseId(1) // Java Full Stack
                .classMode("Online")
                .enqStatus("Enrolled")
                .counsellorId(1)
                .build();
            
            enquiryRepo.save(guaranteedEnq);
            System.out.println("=== GUARANTEED TEST ENQUIRY CREATED ===");
            System.out.println("Student: Test Student");
            System.out.println("Course ID: 1 (Java Full Stack)");
            System.out.println("Status: Enrolled");
            System.out.println("Counsellor ID: 1");
            System.out.println("This enquiry WILL appear when filtering by Java Full Stack");
        } else {
            System.out.println("=== GUARANTEED TEST ENQUIRY ALREADY EXISTS ===");
            System.out.println("Student: " + existingEnq.getStuName());
            System.out.println("Course ID: " + existingEnq.getCourseId());
            System.out.println("Status: " + existingEnq.getEnqStatus());
            System.out.println("Counsellor ID: " + existingEnq.getCounsellorId());
        }
    }
}
