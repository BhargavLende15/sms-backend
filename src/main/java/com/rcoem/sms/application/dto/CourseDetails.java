package com.rcoem.sms.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDetails {
    private String id;
    private String title;
    private String description;
    private String department;
    private Integer capacity;
    private String createdBy;
}

