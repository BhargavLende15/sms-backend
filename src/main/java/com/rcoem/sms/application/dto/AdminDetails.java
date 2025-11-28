package com.rcoem.sms.application.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDetails {
    String id;
    String name;
    String email;
    String mobileNumber;//mobile_number
    String department;
    String gender;
    String dateOfBirth;//date_of_birth
}
