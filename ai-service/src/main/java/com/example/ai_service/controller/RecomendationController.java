package com.example.ai_service.controller;

import com.example.ai_service.model.Recomendation;
import com.example.ai_service.service.RecomendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recomen")
@RequiredArgsConstructor
public class RecomendationController {
    private final RecomendationService recomendationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recomendation>> getUserRecomendation(@PathVariable int userId){
        return ResponseEntity.ok(recomendationService.getUserRecomendation(userId));
    }
    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<Recomendation>> getActivityRecomendation(@PathVariable int activityId){
        return ResponseEntity.ok(recomendationService.getActivityRecomendation(activityId));
    }
}
