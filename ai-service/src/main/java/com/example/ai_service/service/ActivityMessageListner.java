package com.example.ai_service.service;

import com.example.ai_service.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListner {
    @Value("${rabbitmq.queue.name}")
    private String queueName;

    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void processActivity(Activity activity){
        log.info("Processing activity: {}", activity.getId());

    }
}
