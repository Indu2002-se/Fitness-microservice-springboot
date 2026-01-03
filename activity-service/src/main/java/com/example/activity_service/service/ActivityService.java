package com.example.activity_service.service;

import com.example.activity_service.DTO.ActivityRequest;
import com.example.activity_service.DTO.ActivityResponse;
import com.example.activity_service.model.Activity;
import com.example.activity_service.repository.ActivityRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService {
    private final ActivityRepo activityRepo;
    private final UserValidation userValidation;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public Mono<ActivityResponse> createActivity(ActivityRequest activityRequest){
        return userValidation.validateUser(activityRequest.getUserId())
            .flatMap(isValidUser -> {
                if(!isValidUser){
                    return Mono.error(new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "User not found: " + activityRequest.getUserId()
                    ));
                }

                Activity activity = Activity.builder()
                    .userId(activityRequest.getUserId())
                    .activityType(activityRequest.getActivityType())
                    .duration(activityRequest.getDuration())
                    .calories(activityRequest.getCalories())
                    .build();

                Activity savedActivity = activityRepo.save(activity);
                //publish to rabbitmq for ai processing
                try{
                    rabbitTemplate.convertAndSend(exchange, routingKey, savedActivity );
                }catch(Exception e){
                    log.error("Failed to publish to rabbitmq : ",e);

                }

                return Mono.just(toResponse(savedActivity));
            });
    }
    private ActivityResponse toResponse(Activity activity){
        ActivityResponse response = new ActivityResponse();
        response.setUserId(activity.getUserId());
        response.setActivityType(activity.getActivityType());
        response.setDuration(activity.getDuration());
        response.setCalories(activity.getCalories());
       return response;
    }

    public  List<ActivityResponse> getAllActivities(int userId) {
       List<Activity> activities = activityRepo.findByUserId(userId);
       return activities.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public  ActivityResponse getActivities(Long activityId) {
        return activityRepo.findById(activityId)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Activity not found"));

    }
}
