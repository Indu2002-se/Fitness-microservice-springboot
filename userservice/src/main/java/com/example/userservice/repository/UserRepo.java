package com.example.userservice.repository;

import com.example.userservice.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    boolean existsByEmail(@NotBlank(message = "Email is Required") @Email(message = "Invalid email") String email);
}
