package com.lsm.task.dto;

import com.lsm.task.domain.teacher.Teacher;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class RecommendTeacherResponse {
    private final String name;
    private final String phoneNumber;

    public static RecommendTeacherResponse of(Teacher teacher) {
        return new RecommendTeacherResponse(teacher.getName(), teacher.getPhoneNumber());
    }
}
