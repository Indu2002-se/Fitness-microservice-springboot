package com.example.activity_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserValidation {

    private final WebClient userServiceWebClient;

    public Mono<Boolean> validateUser(int userId){
        if (userId <= 0) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid userId"));
        }
        return userServiceWebClient.get()
                .uri("/user/{userId}/validate", userId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    if(e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid userId"));
                    }
                    if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.just(false);
                    }
                    return Mono.just(false);
                });
    }
}
