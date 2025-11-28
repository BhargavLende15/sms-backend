package com.rcoem.sms.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "admin_info")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminInfo {
    @Id
    String id;
    String name;
    String email;
    String mobileNumber;//mobile_number
    String department;
    String gender;
    String dateOfBirth;//date_of_birth
}
