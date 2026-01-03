package com.example.activity_service.repository;

import com.example.activity_service.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepo extends JpaRepository<Activity, Long> {
    List<Activity> findByUserId(int userId);
}
