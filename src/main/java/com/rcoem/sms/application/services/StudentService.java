package com.rcoem.sms.application.services;

import com.rcoem.sms.application.dto.StudentDetails;

import java.util.List;

public interface StudentService {
    List<StudentDetails> getAllStudents();
    StudentDetails createStudent(StudentDetails studentDetails);
    StudentDetails getStudentById(String id);
    StudentDetails updateStudentById(StudentDetails studentDetails);
    void deleteStudentById(StudentDetails studentDetails);
}
