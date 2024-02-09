package com.lsm.task.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlertType {
    SMS(0, "SMS"),
    EMAIL(1, "이메일"),
    APP_PUSH(2, "앱 푸시알림");

    private final int value;
    private final String description;
}
