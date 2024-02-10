package com.lsm.task.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RecommendTeacherRequest {
    @NotNull(message = "공고 ID는 필수 값입니다.")
    private final Long jobPostingId;

    @NotNull(message = "위도는 필수 값입니다.")
    private final double latitude;

    @NotNull(message = "경도는 필수 값입니다.")
    private final double longitude;

    @NotNull(message = "주소는 필수 값입니다.")
    private final String address;

    @NotNull(message = "우편번호 코드는 필수 값입니다.")
    private final String postalCode;
}
