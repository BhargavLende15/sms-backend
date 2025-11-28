package com.rcoem.sms.intefaces;

import com.rcoem.sms.application.dto.CourseDetails;
import com.rcoem.sms.application.dto.EnrollmentRequest;
import com.rcoem.sms.application.exceptions.DuplicateResourceException;
import com.rcoem.sms.application.exceptions.UserNotFoundException;
import com.rcoem.sms.application.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody CourseDetails courseDetails){
        try{
            CourseDetails saved = courseService.createCourse(courseDetails);
            return ResponseEntity.created(URI.create("/courses/"+saved.getId()))
                    .body(saved);
        } catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<CourseDetails>> getCourses(){
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<CourseDetails>> getCoursesForStudent(@PathVariable String studentId){
        return ResponseEntity.ok(courseService.getCoursesForStudent(studentId));
    }

    @PostMapping("/{courseId}/enroll")
    public ResponseEntity<?> enrollStudent(@PathVariable String courseId, @RequestBody EnrollmentRequest request){
        try{
            courseService.enrollStudent(courseId, request.getStudentId());
            return ResponseEntity.ok("Student enrolled successfully");
        } catch (DuplicateResourceException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (UserNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}

