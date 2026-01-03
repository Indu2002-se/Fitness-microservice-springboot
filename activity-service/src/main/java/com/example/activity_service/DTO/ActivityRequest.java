package com.example.activity_service.DTO;

import com.example.activity_service.model.ActivityType;
import lombok.Data;

@Data
public class ActivityRequest {
    private Long id;
    private int userId;
    private ActivityType activityType;
    private int duration;
    private int calories;
}
