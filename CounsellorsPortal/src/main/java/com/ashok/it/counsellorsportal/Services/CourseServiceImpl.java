package com.ashok.it.counsellorsportal.Services;

import com.ashok.it.counsellorsportal.Model.Course;
import com.ashok.it.counsellorsportal.Repository.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepo courseRepo;

    @Override
    public List<Course> getCourses() {
        return courseRepo.findAll();
    }
}
