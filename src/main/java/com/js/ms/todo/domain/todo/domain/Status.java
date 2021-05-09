package com.js.ms.todo.domain.todo.domain;

import lombok.Getter;

@Getter
public enum Status {

    PLAN("계획", "yellow"),
    PROGRESS("진행", "green"),
    COMPLETE("완료", "blue"),
    PROBLEM("문제", "red"),
    HOLD("보류", "grey")
    ;

    private String name;
    private String color;

    Status(String name, String color) {
        this.name = name;
        this.color = color;
    }

}
