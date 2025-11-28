package com.rcoem.sms.domain.repositories;

import com.rcoem.sms.domain.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, String> {

    Optional<UserInfo> findByEmail(String email);
    Optional<UserInfo> findByMobileNumber(String mobileNumber);
}
