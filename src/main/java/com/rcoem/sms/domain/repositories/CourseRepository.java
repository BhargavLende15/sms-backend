package com.rcoem.sms.domain.repositories;

import com.rcoem.sms.domain.entities.CourseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<CourseInfo, String> {
}



