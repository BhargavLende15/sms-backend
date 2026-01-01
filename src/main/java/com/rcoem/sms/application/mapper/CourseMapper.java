package com.rcoem.sms.application.mapper;

import com.rcoem.sms.application.dto.CourseDetails;
import com.rcoem.sms.domain.entities.CourseInfo;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CourseDetails toDto(CourseInfo courseInfo){
        if(courseInfo == null){
            return null;
        }
        return CourseDetails.builder()
                .id(courseInfo.getId())
                .title(courseInfo.getTitle())
                .description(courseInfo.getDescription())
                .department(courseInfo.getDepartment())
                .capacity(courseInfo.getCapacity())
                .createdBy(courseInfo.getCreatedBy())
                .build();
    }

    public CourseInfo toEntity(CourseDetails courseDetails){
        if(courseDetails == null){
            return null;
        }
        return CourseInfo.builder()
                .id(courseDetails.getId())
                .title(courseDetails.getTitle())
                .description(courseDetails.getDescription())
                .department(courseDetails.getDepartment())
                .capacity(courseDetails.getCapacity())
                .createdBy(courseDetails.getCreatedBy())
                .build();
    }
}



