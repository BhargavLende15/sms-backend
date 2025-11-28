package com.rcoem.sms.application.services;

import com.rcoem.sms.application.dto.StudentDetails;
import com.rcoem.sms.application.mapper.StudentMapper;
import com.rcoem.sms.domain.entities.StudentInfo;
import com.rcoem.sms.domain.repositories.StudentRepository;
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
        StudentInfo studentInfo = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        int updatedPoints = (studentInfo.getPoints() == null ? 0 : studentInfo.getPoints()) + points;
        studentInfo.setPoints(updatedPoints);
        StudentInfo saved = studentRepository.save(studentInfo);
        return studentMapper.toDto(saved);
    }
}
