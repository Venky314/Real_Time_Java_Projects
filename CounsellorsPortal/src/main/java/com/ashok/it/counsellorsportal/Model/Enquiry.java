package com.ashok.it.counsellorsportal.Model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "enquiries_tbl")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Enquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enq_id")
    private Integer enqId;

    @Column(name = "stu_name")
    private String stuName;

    @Column(name = "stu_phno")
    private String stuPhno;

    @Column(name = "class_mode")
    private String classMode;

    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "enq_status")
    private String enqStatus;

    @Column(name = "counsellor_id")
    private Integer counsellorId;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Transient
    private String courseName;
}
