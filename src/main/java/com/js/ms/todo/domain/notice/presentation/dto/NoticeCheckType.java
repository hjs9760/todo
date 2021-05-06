package com.js.ms.todo.domain.notice.presentation.dto;

import lombok.Getter;

@Getter
public enum NoticeCheckType {
    NONE("NONE"),
    TRUE("TRUE"),
    FALSE("FALSE"),;

    private String type;

    NoticeCheckType(String type) {
        this.type = type;
    }
}
