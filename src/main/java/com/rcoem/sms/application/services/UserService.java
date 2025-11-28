package com.rcoem.sms.application.services;

import com.rcoem.sms.application.dto.StudentDetails;
import com.rcoem.sms.application.dto.UserDetails;
import org.apache.catalina.User;

import java.util.List;

public interface UserService {
    UserDetails registerUser(UserDetails userDetails);
    UserDetails signInUser(String email,String password);
    UserDetails getUserDetails(UserDetails userDetails);
    void updateUserType(UserDetails userDetails);
}
