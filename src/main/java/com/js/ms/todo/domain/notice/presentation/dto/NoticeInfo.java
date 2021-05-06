package com.js.ms.todo.domain.notice.presentation.dto;

import com.js.ms.todo.domain.notice.domain.Notice;
import com.js.ms.todo.domain.notice.domain.NoticeType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoticeInfo {
    private Long noticeId;

    private Long categoryId;

    private String fromName;

    private String text;

    private String noticeTypeName;

    private Integer noticeType;

    private LocalDateTime registTime;

    private NoticeCheckType noticeCheck;


    public static NoticeInfo convertFrom(Notice notice) {
        NoticeInfo noticeInfo = new NoticeInfo();

        noticeInfo.noticeId = notice.getId();
        if(notice.getNoticeType() == NoticeType.SHARECATEGORY) noticeInfo.categoryId = notice.getCategoryId();
        noticeInfo.fromName = notice.getFromName();
        noticeInfo.text = notice.getText();
        noticeInfo.noticeTypeName = notice.getNoticeType().getName();
        noticeInfo.noticeType = notice.getNoticeType().getType();
        noticeInfo.registTime = notice.getRegistTime();
        noticeInfo.noticeCheck = notice.getNoticeCheck();

        return noticeInfo;
    }
}
