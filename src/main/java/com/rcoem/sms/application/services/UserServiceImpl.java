package com.rcoem.sms.application.services;

import com.rcoem.sms.application.dto.StudentDetails;
import com.rcoem.sms.application.dto.UserDetails;
import com.rcoem.sms.application.exceptions.InvalidCredentialsException;
import com.rcoem.sms.application.exceptions.UserNotFoundException;
import com.rcoem.sms.application.mapper.UserMapper;
import com.rcoem.sms.domain.entities.UserInfo;
import com.rcoem.sms.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    StudentService studentService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public UserDetails registerUser(UserDetails userDetails) {
        String uid = "USER" + UUID.randomUUID();
        userDetails.setId(uid);
        if(userDetails.getType()==null || userDetails.getType().isBlank()){
            userDetails.setType("student");
        }
        userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        UserDetails userDetails1= userMapper.toDto(userRepository.save(userMapper.toEntity(userDetails)));
        userDetails1.setPassword(null);
        if(userDetails.getType().equalsIgnoreCase("student")) {
            studentService.createStudent(StudentDetails.builder()
                    .id(userDetails.getId())
                    .name(userDetails.getName())
                    .email(userDetails.getEmail())
                    .gender(userDetails.getGender())
                    .points(0)
                    .build());
        }
        return userDetails1;
    }

    @Override
    public UserDetails signInUser(String email,String password) {
        UserInfo userDetails=userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User not found with email "+email));
        if(nonNull(userDetails)){
            if(passwordEncoder.matches(password,userDetails.getPassword())){
                UserDetails response = userMapper.toDto(userDetails);
                response.setPassword(null);
                return response;
            }
            throw new InvalidCredentialsException("Invalid credentials");
        }
        throw new InvalidCredentialsException("Invalid credentials");
    }

    @Override
    public UserDetails getUserDetails(UserDetails userDetails) {
        return null;
    }

    @Override
    public void updateUserType(UserDetails userDetails) {

    }
}
