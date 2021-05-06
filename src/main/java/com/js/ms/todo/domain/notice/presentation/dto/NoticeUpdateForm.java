package com.js.ms.todo.domain.notice.presentation.dto;


import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class NoticeUpdateForm {

    @NotNull
    private Long noticeId;

    @NotNull
    private NoticeCheckType noticeCheck;
}
