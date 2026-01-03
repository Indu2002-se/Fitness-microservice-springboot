package com.example.userservice.service;

import com.example.userservice.DTO.RegisterRequest;
import com.example.userservice.DTO.UserResponse;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    public  UserResponse register(RegisterRequest registerRequest) {
        if (userRepo.existsByEmail(registerRequest.getEmail())){
            throw new RuntimeException("email already exists");
        }
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());

        User savedUser = userRepo.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setId(savedUser.getId());
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setPassword(savedUser.getPassword());
        userResponse.setFirstName(savedUser.getFirstName());
        userResponse.setLastName(savedUser.getLastName());
        userResponse.setCreatedAt(savedUser.getCreatedAt());
        userResponse.setUpdateAt(savedUser.getUpdateAt());
        return userResponse;


    }

    public  UserResponse getUserProfile(int userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()-> new RuntimeException("user not found"));

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdateAt(user.getUpdateAt());
        return userResponse;
    }

    public @Nullable Boolean existByUserId(int userId) {
        return userRepo.existsById(userId);
    }
}
