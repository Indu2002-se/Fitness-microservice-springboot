package com.example.userservice.controller;

import com.example.userservice.DTO.RegisterRequest;
import com.example.userservice.DTO.UserResponse;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}/validate")
    public ResponseEntity<Boolean> getUserProfile(@PathVariable int userId){
        return ResponseEntity.ok(userService.existByUserId(userId));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(userService.register(registerRequest));
    }
}
