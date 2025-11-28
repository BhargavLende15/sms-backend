package com.rcoem.sms.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "course_info")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseInfo {
    @Id
    private String id;
    private String title;
    private String description;
    private String department;
    private Integer capacity;
    private String createdBy;
}

