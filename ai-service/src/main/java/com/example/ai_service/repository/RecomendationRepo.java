package com.example.ai_service.repository;

import com.example.ai_service.model.Recomendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecomendationRepo extends JpaRepository<Recomendation,Integer> {
    List<Recomendation> findByUserId(int userId);

    Optional<Recomendation> findByActivityId(int activityId);
}
