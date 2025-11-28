package com.rcoem.sms.application.services;

import com.rcoem.sms.application.dto.StudentDetails;
import com.rcoem.sms.application.mapper.StudentMapper;
import com.rcoem.sms.domain.entities.StudentInfo;
import com.rcoem.sms.domain.entities.UserInfo;
import com.rcoem.sms.domain.repositories.StudentRepository;
import com.rcoem.sms.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<StudentDetails> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(studentInfo -> studentMapper.toDto(studentInfo))
                .toList();
    }

    @Override
    public StudentDetails createStudent(StudentDetails studentDetails) {
        String uid = "RCOEM" + UUID.randomUUID();
        studentDetails.setId(uid);
        StudentInfo insertedRecord = studentRepository.save(studentMapper.toEntity(studentDetails));
        return studentMapper.toDto(insertedRecord);
    }

    @Override
    public StudentDetails getStudentById(String id) {
        Optional<StudentInfo> studentInfo= studentRepository.findById(id);
        return studentInfo.isPresent()? studentMapper.toDto(studentInfo.get()):null;
    }

    @Override
    public StudentDetails updateStudentById(StudentDetails studentDetails) {
        StudentInfo insertedRecord = studentRepository.save(studentMapper.toEntity(studentDetails));
        return studentMapper.toDto(insertedRecord);
    }

    @Override
    public void deleteStudentById(StudentDetails studentDetails) {
       studentRepository.delete(studentMapper.toEntity(studentDetails));
    }

    @Override
    public StudentDetails addPoints(String studentId, Integer points, String awardedBy) {
        if(studentId == null || studentId.trim().isEmpty()){
            throw new IllegalArgumentException("studentId is required");
        }
        if(points == null || points <=0){
            throw new IllegalArgumentException("Points should be greater than zero");
        }
        if(awardedBy == null || awardedBy.trim().isEmpty()){
            throw new IllegalArgumentException("awardedBy (teacher id) is required");
        }
        UserInfo awardingUser = userRepository.findById(awardedBy)
                .orElseThrow(() -> new RuntimeException("Awarding user not found"));
        if (!"teacher".equalsIgnoreCase(awardingUser.getType())) {
            throw new RuntimeException("Only teachers can add points");
        }
        StudentInfo studentInfo = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        int updatedPoints = (studentInfo.getPoints() == null ? 0 : studentInfo.getPoints()) + points;
        studentInfo.setPoints(updatedPoints);
        StudentInfo saved = studentRepository.save(studentInfo);
        return studentMapper.toDto(saved);
    }
}
