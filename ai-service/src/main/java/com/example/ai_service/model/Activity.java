package com.example.ai_service.model;

import lombok.Data;

import java.time.LocalDateTime;


@Data

public class Activity {

    private Long id;
    private int userId;
    private String type;
    private int duration;
    private int calories;
    private LocalDateTime startTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
