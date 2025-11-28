package com.rcoem.sms.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StudentDetails {
    String rollNo;
    String id;
    String name;
    String course;
    String email;
    String mobileNumber;
    String department;
    String gender;
    String dateOfBirth;
    Integer points;

}
