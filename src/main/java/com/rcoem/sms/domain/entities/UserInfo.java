package com.rcoem.sms.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Entity(name = "user_info")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    @Id
    String id;
    String name;
    @NonNull
    String type;
    @NonNull
    @Column(unique = true)
    String email;
    @Column(unique = true)
    String mobileNumber;
    @NonNull
    String password;
    String gender;
    String dateOfBirth;
    String department;
}
