package com.rcoem.sms.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity(name = "course_enrollment")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"course_id","student_id"}))
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseEnrollmentInfo {
    @Id
    private String id;

    @Column(name = "course_id", nullable = false)
    private String courseId;

    @Column(name = "student_id", nullable = false)
    private String studentId;

    private String status;
    private LocalDateTime enrolledAt;
}



