package com.ashok.it.counsellorsportal.Config;

import com.ashok.it.counsellorsportal.Model.Course;
import com.ashok.it.counsellorsportal.Repository.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CourseRepo courseRepo;

    @Override
    public void run(String... args) throws Exception {
        // Check if courses already exist
        if (courseRepo.count() == 0) {
            // Initialize courses
            List<Course> courses = Arrays.asList(
                Course.builder().courseName("Java Full Stack").build(),
                Course.builder().courseName("Python Full Stack").build(),
                Course.builder().courseName("React JS").build(),
                Course.builder().courseName("Angular").build(),
                Course.builder().courseName("Node.js").build(),
                Course.builder().courseName("Spring Boot").build(),
                Course.builder().courseName("Hibernate").build(),
                Course.builder().courseName("JPA").build(),
                Course.builder().courseName("MySQL").build(),
                Course.builder().courseName("MongoDB").build(),
                Course.builder().courseName("AWS").build(),
                Course.builder().courseName("DevOps").build(),
                Course.builder().courseName("Docker").build(),
                Course.builder().courseName("Kubernetes").build(),
                Course.builder().courseName("Microservices").build(),
                Course.builder().courseName("REST API").build(),
                Course.builder().courseName("GraphQL").build(),
                Course.builder().courseName("HTML/CSS/JavaScript").build(),
                Course.builder().courseName("TypeScript").build(),
                Course.builder().courseName("Vue.js").build()
            );
            
            courseRepo.saveAll(courses);
            System.out.println("Successfully initialized " + courses.size() + " courses!");
        } else {
            System.out.println("Courses already exist in database. Count: " + courseRepo.count());
        }
    }
}
