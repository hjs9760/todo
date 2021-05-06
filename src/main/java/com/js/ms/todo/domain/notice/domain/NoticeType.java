package com.js.ms.todo.domain.notice.domain;

import lombok.Getter;

@Getter
public enum NoticeType {
    ENDTODO("할일마감", 1),
    SHARECATEGORY("공유", 2),
    UPDATECATEGORY("변경", 3),
    ;

    private String name;
    private int type;

    NoticeType(String name, int type) {
        this.name = name;
        this.type = type;
    }


}
