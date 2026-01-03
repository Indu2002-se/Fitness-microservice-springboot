package com.example.activity_service.controller;

import com.example.activity_service.DTO.ActivityRequest;
import com.example.activity_service.DTO.ActivityResponse;
import com.example.activity_service.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/activity")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;

    @PostMapping
    public Mono<ResponseEntity<ActivityResponse>> createActivity(@RequestBody ActivityRequest activityRequest){
        return activityService.createActivity(activityRequest)
                .map(ResponseEntity::ok);
    }
    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getAllActivities(@RequestHeader("userId") int userId){
        return ResponseEntity.ok(activityService.getAllActivities(userId));
    }
    @GetMapping("{activityId}")
    public ResponseEntity<ActivityResponse> getActivities(@PathVariable Long activityId){
        return ResponseEntity.ok(activityService.getActivities(activityId));
    }
}
