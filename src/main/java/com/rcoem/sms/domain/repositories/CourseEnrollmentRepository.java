package com.rcoem.sms.domain.repositories;

import com.rcoem.sms.domain.entities.CourseEnrollmentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollmentInfo, String> {
    Optional<CourseEnrollmentInfo> findByCourseIdAndStudentId(String courseId, String studentId);
    List<CourseEnrollmentInfo> findAllByStudentId(String studentId);
}

