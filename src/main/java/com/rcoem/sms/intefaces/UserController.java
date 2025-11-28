package com.rcoem.sms.intefaces;

import com.rcoem.sms.application.dto.LoginRequest;
import com.rcoem.sms.application.dto.UserDetails;
import com.rcoem.sms.application.exceptions.DuplicateResourceException;
import com.rcoem.sms.application.exceptions.InvalidCredentialsException;
import com.rcoem.sms.application.exceptions.UserNotFoundException;
import com.rcoem.sms.application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody UserDetails userDetails) {
        try {
            UserDetails insertedStudentDetails=userService.registerUser(userDetails);
            return ResponseEntity.created(URI.create("/users/"+insertedStudentDetails.getId()))
                    .body(insertedStudentDetails);
        } catch (DuplicateResourceException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signInUser(@RequestBody LoginRequest loginRequest) {
        try {
            UserDetails studentDetails= userService.signInUser(
                    loginRequest.getEmail(),loginRequest.getPassword());
            return ResponseEntity.ok(studentDetails);
        } catch (UserNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (InvalidCredentialsException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody UserDetails userDetails) {
        try{
            UserDetails updatedUser = userService.updateUserDetails(id, userDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (DuplicateResourceException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (UserNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

}
