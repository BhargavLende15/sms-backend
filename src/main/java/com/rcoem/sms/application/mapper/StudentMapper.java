package com.rcoem.sms.application.mapper;

import com.rcoem.sms.application.dto.StudentDetails;
import com.rcoem.sms.domain.entities.StudentInfo;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public StudentDetails toDto(StudentInfo studentInfo) {
        return StudentDetails.builder()
                .id(studentInfo.getId())
                .rollNo(studentInfo.getRollNo())
                .name(studentInfo.getName())
                .email(studentInfo.getEmail())
                .department(studentInfo.getDepartment())
                .gender(studentInfo.getGender())
                .course(studentInfo.getCourse())
                .points(studentInfo.getPoints())
                .build();
    }
    public StudentInfo toEntity(StudentDetails studentDetails) {
        return StudentInfo.builder()
                .id(studentDetails.getId())
                .rollNo(studentDetails.getRollNo())
                .name(studentDetails.getName())
                .email(studentDetails.getEmail())
                .department(studentDetails.getDepartment())
                .gender(studentDetails.getGender())
                .course(studentDetails.getCourse())
                .points(studentDetails.getPoints())
                .build();
    }
}
