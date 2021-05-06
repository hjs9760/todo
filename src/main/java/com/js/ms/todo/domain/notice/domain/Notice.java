package com.js.ms.todo.domain.notice.domain;

import com.js.ms.todo.domain.member.domain.Member;
import com.js.ms.todo.domain.notice.presentation.dto.NoticeCheckType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column
    private Long categoryId;

    @Column
    private String fromName;

    @Column
    private String text;

    @Enumerated(EnumType.STRING)
    @Column
    private NoticeType noticeType;

    @NotNull
    private LocalDateTime registTime;

    @Column
    @Enumerated(EnumType.STRING)
    @Builder.Default private NoticeCheckType noticeCheck = NoticeCheckType.NONE;


    public void changeNotice(NoticeCheckType noticeCheck) {
        this.noticeCheck = noticeCheck;
    }

}
