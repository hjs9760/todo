package com.js.ms.todo.domain.notice.presentation.dto;

import com.js.ms.todo.domain.notice.domain.NoticeType;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
public class NoticeSaveForm {

    @NotNull
    private Long categoryId;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotNull
    private NoticeType noticeType;

}
