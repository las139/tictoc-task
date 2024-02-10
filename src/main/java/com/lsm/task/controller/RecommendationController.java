package com.lsm.task.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lsm.task.dto.RecommendTeacherRequest;
import com.lsm.task.dto.RecommendTeacherResponse;
import com.lsm.task.service.RecommendTeacherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@Tag(name = "Recommendation", description = "추천 API Document")
public class RecommendationController {
    private final RecommendTeacherService recommendTeacherService;

    @Operation(summary = "공고 추천 교사 목록 조회", description = "해당 공고 주소에 맞는 추천 교사 목록을 조회한다.")
    @PostMapping("/recommend/teacher")
    public ResponseEntity<List<RecommendTeacherResponse>> recommendTeachers(@Valid @RequestBody RecommendTeacherRequest request) {
        return ResponseEntity.ok().body(recommendTeacherService.findMatchingTeachers(request));
    }
}
