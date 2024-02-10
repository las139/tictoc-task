package com.lsm.task.core.payload;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiResponse<T extends Object> {
    private static final String OK_MESSAGE = "OK";

    private int status;
    private String message;
    private String cause;
    private String error;
    private long timestamp;
    private T data;

    public static <T> ApiResponse<?> ofErrorResponse(int status, String code, String message, T data) {
        return ApiResponse.builder()
                          .status(status)
                          .cause(code)
                          .message(message)
                          .data(data)
                          .build();
    }

    public static <T> ApiResponse<?> ofSuccessResponse(T data) {
        return ApiResponse.builder()
                          .status(HttpStatus.OK.value())
                          .message(OK_MESSAGE)
                          .data(data)
                          .build();
    }
}
