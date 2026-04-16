package com.ashok.it.counsellorsportal.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "courses_offering")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "course_name")
    private String courseName;
}
