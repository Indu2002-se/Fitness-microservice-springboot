package com.example.ai_service.service;

import com.example.ai_service.model.Recomendation;
import com.example.ai_service.repository.RecomendationRepo;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecomendationService {
    private final RecomendationRepo recomendationRepo;

    public  List<Recomendation> getUserRecomendation(int userId) {
        return recomendationRepo.findByUserId(userId);
    }

    public  List<Recomendation> getActivityRecomendation(int activityId) {
        return Collections.singletonList(recomendationRepo.findByActivityId(activityId)
                .orElseThrow(() -> new RuntimeException("No recomendation found" + activityId)));
    }
}
