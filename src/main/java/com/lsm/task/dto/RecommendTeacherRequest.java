package com.lsm.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RecommendTeacherRequest {
    @Schema(description = "공고 ID", nullable = false, example = "1")
    @NotNull(message = "공고 ID는 필수 값입니다.")
    private final Long jobPostingId;

    @Schema(description = "위도", nullable = false, example = "127.115888963")
    @NotNull(message = "위도는 필수 값입니다.")
    private final double latitude;

    @Schema(description = "경도", nullable = false, example = "37.295558048")
    @NotNull(message = "경도는 필수 값입니다.")
    private final double longitude;

    @Schema(description = "주소", nullable = false, example = "경기도 용인시 기흥구 구성로53번길 8 (마북동,용인 구성 지음재 아파트)")
    @NotNull(message = "주소는 필수 값입니다.")
    private final String address;

    @Schema(description = "우편번호 코드", nullable = false, example = "16917")
    @NotNull(message = "우편번호 코드는 필수 값입니다.")
    private final String postalCode;
}
