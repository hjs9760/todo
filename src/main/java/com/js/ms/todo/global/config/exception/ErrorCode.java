package com.js.ms.todo.global.config.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Common
    // input type 예외
    INVALID_INPUT_VALUE( "A001"),
    // 미존재 데이터 예외
    NOT_FOUND_ROW("A002"),
    // 종복 예외
    DUPLICATE_ROW("A003"),
    // Server 예외
    INTERNAL_SERVER_ERROR("A010"),


    // Member
    // 회원가입 예외
    MEMBER_SIGNUP_FAIL("B00", "Member Signup Fail"),
    // 인증 예외
    MEMBER_AUTHENTICATION_FAIL("B001", "회원 인증에 실패했습니다."),
    // 인가 예외
    MEMBER_AUTHORIZATION_DENIED("B002", "Member AUTHORIZATION Exception"),
    // 회원가입 후 이메일 인증 예외
    MEMBER_SIGNUP_EMAIL_FAIL("B003", "이메일 인증에 실패했습니다. \n인증해 주세요."),
    // 로그인 실패 예외
    MEMBER_SIGNIN_ID_FAIL("B004", "존재하지 않는 아이디입니다."),
    // 회원 가입 이메일 중복 예외
    MEMBER_SIGNIN_EMAIL_DUPLICATE("B004", "이미 가입된 이메일이 존재합니다."),

    // Category
    // 카테고리 생성 예외
    CATEGORY_SAVE_FAIL("C00", "카테고리 생성에 실패했습니다."),
    // 카테고리 접근 예외
    CATEGORY_AUTH_FAIL("C01", "해당 카테고리 접근권한이 없습니다."),
    // 카테고리 공유 예외
    CATEGORY_SHARE_FAIL("C02", "카테고리 공유에 실패했습니다."),

    // Category
    // 섹션 생성 예외
    SECTION_SAVE_FAIL("D00", "섹션 생성에 실패했습니다."),
    // 섹션 수정 실패
    SECTION_UPDATE_FAIL("D01", "섹션 수정에 실패했습니다."),

    // TodoList
    // 할일 생성 예외
    TODO_SAVE_FAIL("E00", "할일 생성에 실패했습니다."),
    // 할일 수정 예외
    TODO_UPDATE_FAIL("E01", "할일 수정에 실패했습니다."),

    // Notice
    // 알림 저장 예외
    NOTICE_SAVE_FAIL("F00", "공유 알림 전송에 실패했습니다.");


    private String code;
    private String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    ErrorCode(String code) {
        this.code = code;
    }
}
