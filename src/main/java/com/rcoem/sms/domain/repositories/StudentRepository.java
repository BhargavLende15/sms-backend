package com.rcoem.sms.domain.repositories;

import com.rcoem.sms.domain.entities.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentInfo, String> {
}
