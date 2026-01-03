package com.example.activity_service.DTO;

import com.example.activity_service.model.ActivityType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
public class ActivityResponse {
    private Long id;
    private int userId;
    private ActivityType activityType;
    private int duration;
    private int calories;
    private LocalDateTime startTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
