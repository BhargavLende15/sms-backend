package com.rcoem.sms.domain.repositories;

import com.rcoem.sms.domain.entities.AdminInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminInfo, String> {
}



