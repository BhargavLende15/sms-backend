package com.rcoem.sms.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointsUpdateRequest {
    private Integer points;
    private String awardedBy;
}

