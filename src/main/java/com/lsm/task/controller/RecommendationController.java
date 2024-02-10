package com.lsm.task.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lsm.task.core.payload.ApiResponse;
import com.lsm.task.dto.RecommendTeacherRequest;
import com.lsm.task.service.RecommendTeacherService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class RecommendationController {
    private final RecommendTeacherService recommendTeacherService;

    @PostMapping("/recommend/teacher")
    public ResponseEntity<ApiResponse> recommendTeachers(@Valid @RequestBody RecommendTeacherRequest request) {
        return ResponseEntity.ok(ApiResponse.ofSuccessResponse(recommendTeacherService.findMatchingTeachers(request)));
    }
}
