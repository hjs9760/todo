package com.js.ms.todo.domain.notice.application;

import com.js.ms.todo.domain.category.domain.Category;
import com.js.ms.todo.domain.category.domain.CategoryRepository;
import com.js.ms.todo.domain.member.domain.Member;
import com.js.ms.todo.domain.member.domain.MemberRepository;
import com.js.ms.todo.domain.notice.domain.Notice;
import com.js.ms.todo.domain.notice.domain.NoticeRepository;
import com.js.ms.todo.domain.notice.presentation.dto.NoticeCheckType;
import com.js.ms.todo.domain.notice.presentation.dto.NoticeInfo;
import com.js.ms.todo.domain.notice.presentation.dto.NoticeSaveForm;
import com.js.ms.todo.domain.notice.presentation.dto.NoticeUpdateForm;
import com.js.ms.todo.global.config.Response.Response;
import com.js.ms.todo.global.config.exception.ErrorCode;
import com.js.ms.todo.global.config.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Response save(Long memberId, NoticeSaveForm dto) {
        String fromName = "admin";
        Member toMember = memberRepository.findByEmail(dto.getEmail());
        Category category = categoryRepository.findById(dto.getCategoryId()).get();

        String text = "";
        switch (dto.getNoticeType()) {
            case ENDTODO:
                text = category.getName() + " 마감 기한이 3일 남았습니다.";
                break;
            case SHARECATEGORY:
                fromName = memberRepository.findById(memberId).get().getName();
                text = fromName + " 님이 '" + category.getName() + "'를 공유 하셨습니다.";
                break;
            case UPDATECATEGORY:
                text = category.getName() + " 이 변경 되었습니다.";
                break;
        }

        Notice notice = Notice.builder()
                .member(toMember)
                .categoryId(dto.getCategoryId())
                .fromName(fromName)
                .text(text)
                .noticeType(dto.getNoticeType())
                .registTime(LocalDateTime.now())
                .build();


        if (!ObjectUtils.isEmpty(noticeRepository.save(notice))) return Response.of("200", "공유 알림을 보냈습니다.");
        else return Response.of(ErrorCode.NOTICE_SAVE_FAIL.getCode(), ErrorCode.NOTICE_SAVE_FAIL);
    }

    @Transactional
    public Response delete(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new NotFoundException("해당 알림이 존재하지 않습니다."));
        noticeRepository.delete(notice);

        return Response.of("200", "해당 알림이 삭제 되었습니다.");
    }

    @Transactional(readOnly = true)
    public Response findNoticeAll(Long memberId) {
        List<Notice> notices = noticeRepository.findByMemberId(memberId);
        List<NoticeInfo> noticeInfos = new ArrayList<>();

        for (Notice notice : notices) {
            noticeInfos.add(NoticeInfo.convertFrom(notice));
        }

        return Response.of("200", noticeInfos);
    }

    @Transactional
    public Response checkNotice(NoticeUpdateForm noticeUpdateForm) {
        Notice notice = noticeRepository.findById(noticeUpdateForm.getNoticeId()).get();

        notice.changeNotice(noticeUpdateForm.getNoticeCheck());
        noticeRepository.save(notice);

        if (noticeUpdateForm.getNoticeCheck() == NoticeCheckType.TRUE) return Response.of("200", "동의 처리 되었습니다.");
        else return Response.of("200", "거절 처리 되었습니다.");

    }
}
