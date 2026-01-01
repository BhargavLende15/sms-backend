package com.rcoem.sms.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDetails {
    private String enrollmentId;
    private String studentId;
    private String studentName;
    private String studentEmail;
    private String department;
    private String status;
    private LocalDateTime enrolledAt;
}



