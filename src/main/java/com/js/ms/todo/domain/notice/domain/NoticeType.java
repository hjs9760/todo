package com.js.ms.todo.domain.notice.domain;

import lombok.Getter;

@Getter
public enum NoticeType implements Noticeable {
    ENDTODO("할일마감", 1, "관리자") {
        @Override
        public String getText(String categoryName) {
            return "'" +  categoryName + "'" + " 마감 기한이 3일 남았습니다.";
        }

        public String getText(String categoryName, int date) {
            return "'" +  categoryName + "'" + " 마감 기한이 " + date  + "일 남았습니다.";
        }
    },
    SHARECATEGORY("공유", 2) {
        @Override
        public String getText(String categoryName) {
            return "'" + categoryName + "'" + "를 공유 하셨습니다.";
        }
    },
    UPDATECATEGORY("변경", 3, "관리자") {
        @Override
        public String getText(String categoryName) {
            return categoryName + " 이 변경 되었습니다.";
        }
    },
    ;

    private String name;
    private int type;
    private String fromName;

    NoticeType(String name, int type) {
        this.name = name;
        this.type = type;
    }

    NoticeType(String name, int type, String fromName) {
        this.name = name;
        this.type = type;
        this.fromName = fromName;
    }
}
