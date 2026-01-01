package com.rcoem.sms.application.services;

import com.rcoem.sms.application.dto.CourseDetails;
import com.rcoem.sms.application.dto.EnrollmentDetails;

import java.util.List;

public interface CourseService {
    CourseDetails createCourse(CourseDetails courseDetails);
    List<CourseDetails> getAllCourses();
    List<CourseDetails> getCoursesForStudent(String studentId);
    void enrollStudent(String courseId, String studentId);
    List<EnrollmentDetails> getEnrollmentsForCourse(String courseId);
}

