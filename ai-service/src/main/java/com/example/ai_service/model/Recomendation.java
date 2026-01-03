package com.example.ai_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "recomendation")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Recomendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int activityId;
    private int userId;
    private String activityType;
    private String recomendation;
    private List<String> improvement;
    private List<String> suggestions;
    private List<String> safety;
    private LocalDateTime createdAt;
}
