package com.lsm.task.dto;

import com.lsm.task.domain.teacher.Teacher;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class RecommendTeacherResponse {

    @Schema(description = "교사명")
    private final String name;

    @Schema(description = "휴대전화번호")
    private final String phoneNumber;

    public static RecommendTeacherResponse of(Teacher teacher) {
        return new RecommendTeacherResponse(teacher.getName(), teacher.getPhoneNumber());
    }
}
