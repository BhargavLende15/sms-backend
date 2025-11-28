package com.rcoem.sms.application.services;

import com.rcoem.sms.application.dto.CourseDetails;
import com.rcoem.sms.application.exceptions.DuplicateResourceException;
import com.rcoem.sms.application.exceptions.UserNotFoundException;
import com.rcoem.sms.application.mapper.CourseMapper;
import com.rcoem.sms.domain.entities.CourseEnrollmentInfo;
import com.rcoem.sms.domain.entities.CourseInfo;
import com.rcoem.sms.domain.repositories.CourseEnrollmentRepository;
import com.rcoem.sms.domain.repositories.CourseRepository;
import com.rcoem.sms.domain.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseEnrollmentRepository courseEnrollmentRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public CourseDetails createCourse(CourseDetails courseDetails) {
        validateCourseDetails(courseDetails);
        String id = "COURSE" + UUID.randomUUID();
        courseDetails.setId(id);
        CourseInfo saved = courseRepository.save(courseMapper.toEntity(courseDetails));
        return courseMapper.toDto(saved);
    }

    @Override
    public List<CourseDetails> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toDto)
                .toList();
    }

    @Override
    public List<CourseDetails> getCoursesForStudent(String studentId) {
        if(isBlank(studentId)){
            throw new IllegalArgumentException("studentId is required");
        }
        List<CourseEnrollmentInfo> enrollments = courseEnrollmentRepository.findAllByStudentId(studentId);
        if(enrollments.isEmpty()){
            return List.of();
        }
        Set<String> courseIds = enrollments.stream()
                .map(CourseEnrollmentInfo::getCourseId)
                .collect(java.util.stream.Collectors.toSet());
        return courseRepository.findAllById(courseIds)
                .stream()
                .map(courseMapper::toDto)
                .toList();
    }

    @Override
    public void enrollStudent(String courseId, String studentId) {
        if(isBlank(courseId) || isBlank(studentId)){
            throw new IllegalArgumentException("CourseId and studentId are required");
        }
        CourseInfo courseInfo = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        boolean studentExists = studentRepository.existsById(studentId);
        if(!studentExists){
            throw new UserNotFoundException("Student not found");
        }
        courseEnrollmentRepository.findByCourseIdAndStudentId(courseId, studentId)
                .ifPresent(existing -> { throw new DuplicateResourceException("Student already enrolled in this course");});

        CourseEnrollmentInfo enrollmentInfo = CourseEnrollmentInfo.builder()
                .id("ENROLL" + UUID.randomUUID())
                .courseId(courseInfo.getId())
                .studentId(studentId)
                .status("ENROLLED")
                .enrolledAt(LocalDateTime.now())
                .build();
        courseEnrollmentRepository.save(enrollmentInfo);
    }

    private void validateCourseDetails(CourseDetails courseDetails){
        if(courseDetails == null){
            throw new IllegalArgumentException("Course details are required");
        }
        if(isBlank(courseDetails.getTitle())){
            throw new IllegalArgumentException("Course title is required");
        }
        if(isBlank(courseDetails.getDepartment())){
            throw new IllegalArgumentException("Department is required");
        }
    }

    private boolean isBlank(String value){
        return value == null || value.trim().isEmpty();
    }
}

