package com.rcoem.sms.application.services;

import com.rcoem.sms.application.dto.CourseDetails;
import com.rcoem.sms.application.dto.EnrollmentDetails;
import com.rcoem.sms.application.exceptions.DuplicateResourceException;
import com.rcoem.sms.application.exceptions.UserNotFoundException;
import com.rcoem.sms.application.mapper.CourseMapper;
import com.rcoem.sms.domain.entities.CourseEnrollmentInfo;
import com.rcoem.sms.domain.entities.CourseInfo;
import com.rcoem.sms.domain.entities.StudentInfo;
import com.rcoem.sms.domain.repositories.CourseEnrollmentRepository;
import com.rcoem.sms.domain.repositories.CourseRepository;
import com.rcoem.sms.domain.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    public List<EnrollmentDetails> getEnrollmentsForCourse(String courseId) {
        if(isBlank(courseId)){
            throw new IllegalArgumentException("CourseId is required");
        }
        // Verify course exists
        courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        
        List<CourseEnrollmentInfo> enrollments = courseEnrollmentRepository.findAllByCourseId(courseId);
        
        return enrollments.stream()
                .map(enrollment -> {
                    StudentInfo student = studentRepository.findById(enrollment.getStudentId())
                            .orElse(null);
                    if(student == null){
                        // Student not found, return minimal info
                        return EnrollmentDetails.builder()
                                .enrollmentId(enrollment.getId())
                                .studentId(enrollment.getStudentId())
                                .studentName("Unknown")
                                .studentEmail("N/A")
                                .department("N/A")
                                .status(enrollment.getStatus())
                                .enrolledAt(enrollment.getEnrolledAt())
                                .build();
                    }
                    return EnrollmentDetails.builder()
                            .enrollmentId(enrollment.getId())
                            .studentId(student.getId())
                            .studentName(student.getName())
                            .studentEmail(student.getEmail())
                            .department(student.getDepartment())
                            .status(enrollment.getStatus())
                            .enrolledAt(enrollment.getEnrolledAt())
                            .build();
                })
                .collect(Collectors.toList());
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

