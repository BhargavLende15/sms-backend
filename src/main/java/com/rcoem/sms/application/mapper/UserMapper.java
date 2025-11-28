package com.rcoem.sms.application.mapper;

import com.rcoem.sms.application.dto.UserDetails;
import com.rcoem.sms.domain.entities.UserInfo;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDetails toDto(UserInfo userInfo) {
        return UserDetails.builder()
                .id(userInfo.getId())
                .name(userInfo.getName())
                .email(userInfo.getEmail())
                .password(userInfo.getPassword())
                .gender(userInfo.getGender())
                .type(userInfo.getType())
                .mobileNumber(userInfo.getMobileNumber())
                .dateOfBirth(userInfo.getDateOfBirth())
                .department(userInfo.getDepartment())
                .build();
    }
    public UserInfo toEntity(UserDetails userInfo) {
        return UserInfo.builder()
                .id(userInfo.getId())
                .name(userInfo.getName())
                .email(userInfo.getEmail())
                .password(userInfo.getPassword())
                .gender(userInfo.getGender())
                .type(userInfo.getType())
                .mobileNumber(userInfo.getMobileNumber())
                .dateOfBirth(userInfo.getDateOfBirth())
                .department(userInfo.getDepartment())
                .build();
    }
}
