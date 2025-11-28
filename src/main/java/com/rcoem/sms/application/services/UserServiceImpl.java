package com.rcoem.sms.application.services;

import com.rcoem.sms.application.dto.StudentDetails;
import com.rcoem.sms.application.dto.UserDetails;
import com.rcoem.sms.application.exceptions.DuplicateResourceException;
import com.rcoem.sms.application.exceptions.InvalidCredentialsException;
import com.rcoem.sms.application.exceptions.UserNotFoundException;
import com.rcoem.sms.application.mapper.UserMapper;
import com.rcoem.sms.domain.entities.AdminInfo;
import com.rcoem.sms.domain.entities.TeacherInfo;
import com.rcoem.sms.domain.entities.UserInfo;
import com.rcoem.sms.domain.repositories.AdminRepository;
import com.rcoem.sms.domain.repositories.TeacherRepository;
import com.rcoem.sms.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.regex.Pattern;

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

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    AdminRepository adminRepository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^[0-9]{10}$");


    @Override
    public UserDetails registerUser(UserDetails userDetails) {
        validateUserInput(userDetails);
        ensureUniqueConstraints(userDetails);
        String uid = "USER" + UUID.randomUUID();
        userDetails.setId(uid);
        if(userDetails.getType()==null || userDetails.getType().isBlank()){
            userDetails.setType("student");
        }
        userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        UserDetails savedUser = userMapper.toDto(userRepository.save(userMapper.toEntity(userDetails)));
        savedUser.setPassword(null);
        if(userDetails.getType().equalsIgnoreCase("student")) {
            studentService.createStudent(StudentDetails.builder()
                    .id(userDetails.getId())
                    .name(userDetails.getName())
                    .email(userDetails.getEmail())
                    .gender(userDetails.getGender())
                    .points(0)
                    .build());
        } else if (userDetails.getType().equalsIgnoreCase("teacher")) {
            teacherRepository.save(TeacherInfo.builder()
                    .id(userDetails.getId())
                    .name(userDetails.getName())
                    .email(userDetails.getEmail())
                    .mobileNumber(userDetails.getMobileNumber())
                    .department(userDetails.getDepartment())
                    .gender(userDetails.getGender())
                    .dateOfBirth(userDetails.getDateOfBirth())
                    .build());
        } else if (userDetails.getType().equalsIgnoreCase("admin")) {
            adminRepository.save(AdminInfo.builder()
                    .id(userDetails.getId())
                    .name(userDetails.getName())
                    .email(userDetails.getEmail())
                    .mobileNumber(userDetails.getMobileNumber())
                    .department(userDetails.getDepartment())
                    .gender(userDetails.getGender())
                    .dateOfBirth(userDetails.getDateOfBirth())
                    .build());
        }
        return savedUser;
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

    @Override
    public UserDetails updateUserDetails(String userId, UserDetails userDetails) {
        UserInfo userInfo = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));
        if(nonNull(userDetails.getName())){
            userInfo.setName(userDetails.getName());
        }
        if(nonNull(userDetails.getEmail())){
            validateEmail(userDetails.getEmail());
            userRepository.findByEmail(userDetails.getEmail())
                    .filter(existing -> !existing.getId().equals(userId))
                    .ifPresent(existing -> { throw new DuplicateResourceException("Email already registered");});
            userInfo.setEmail(userDetails.getEmail());
        }
        if(nonNull(userDetails.getMobileNumber())){
            validateMobile(userDetails.getMobileNumber());
            userRepository.findByMobileNumber(userDetails.getMobileNumber())
                    .filter(existing -> !existing.getId().equals(userId))
                    .ifPresent(existing -> { throw new DuplicateResourceException("Mobile number already registered");});
            userInfo.setMobileNumber(userDetails.getMobileNumber());
        }
        if(nonNull(userDetails.getGender())){
            userInfo.setGender(userDetails.getGender());
        }
        if(nonNull(userDetails.getDateOfBirth())){
            userInfo.setDateOfBirth(userDetails.getDateOfBirth());
        }
        if(nonNull(userDetails.getDepartment())){
            userInfo.setDepartment(userDetails.getDepartment());
        }

        UserInfo saved=userRepository.save(userInfo);
        UserDetails response=userMapper.toDto(saved);
        response.setPassword(null);
        return response;
    }

    private void validateUserInput(UserDetails userDetails){
        validateEmail(userDetails.getEmail());
        validateMobile(userDetails.getMobileNumber());
    }

    private void validateEmail(String email){
        if(!EMAIL_PATTERN.matcher(email).matches()){
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    private void validateMobile(String mobileNumber){
        if(nonNull(mobileNumber) && !MOBILE_PATTERN.matcher(mobileNumber).matches()){
            throw new IllegalArgumentException("Invalid mobile number");
        }
    }

    private void ensureUniqueConstraints(UserDetails userDetails){
        userRepository.findByEmail(userDetails.getEmail())
                .ifPresent(existing -> { throw new DuplicateResourceException("Email already registered");});
        if(nonNull(userDetails.getMobileNumber())){
            userRepository.findByMobileNumber(userDetails.getMobileNumber())
                    .ifPresent(existing -> { throw new DuplicateResourceException("Mobile number already registered");});
        }
    }
}
