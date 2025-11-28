package com.rcoem.sms.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Entity(name = "student_info")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentInfo {
    @Id
    String id;
    String rollNo; //roll_no
    String name;
    String course;
    String email;
    String mobileNumber;//mobile_number
    String department;
    String gender;
    String dateOfBirth;//date_of_birth
    Integer points;
}
