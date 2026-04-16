package com.ashok.it.counsellorsportal.Repository;

import com.ashok.it.counsellorsportal.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {
}
