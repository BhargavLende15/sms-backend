package com.rcoem.sms.intefaces;

import com.rcoem.sms.application.dto.PointsUpdateRequest;
import com.rcoem.sms.application.dto.StudentDetails;
import com.rcoem.sms.application.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "*")
public class StudentsController {
    @Autowired
    StudentService studentService;

    @PostMapping
    public ResponseEntity<Void> addStudent(@RequestBody StudentDetails studentDetails) {
        StudentDetails insertedStudentDetails=studentService.createStudent(studentDetails);
        return ResponseEntity.created(URI.create("/students/"+insertedStudentDetails.getId())).build();
    }
    @GetMapping
    public List<StudentDetails> getStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDetails> getStudentById( @PathVariable String id) {
        StudentDetails studentDetails= studentService.getStudentById(id);
        if(studentDetails==null){
          return  ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(studentDetails);
        }
    }

    @PutMapping("/{id}/points")
    public ResponseEntity<?> addPoints(@PathVariable String id, @RequestBody PointsUpdateRequest request){
        try{
            StudentDetails updated = studentService.addPoints(id, request.getPoints(), request.getAwardedBy());
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (RuntimeException ex){
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }

}
