package com.rcoem.sms.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDetails {
    String id;
    String name;
    String type;
    String email;
    String mobileNumber;
    String password;
    String gender;
    String dateOfBirth;
}
