package com.js.ms.todo.global.config.scheduler;

import com.js.ms.todo.domain.member.domain.MemberCategory;
import com.js.ms.todo.domain.notice.domain.Notice;
import com.js.ms.todo.domain.notice.domain.NoticeRepository;
import com.js.ms.todo.domain.notice.domain.NoticeType;
import com.js.ms.todo.domain.todo.domain.Todo;
import com.js.ms.todo.domain.todo.domain.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Job : 실제로 실행되는 로직이 있는 곳입니다. Quartz에서 interface로 제공하며 해당 interface를 구현
 */

@Slf4j
@RequiredArgsConstructor
public class NoticeJob implements Job {

    private final TodoRepository todoRepository;
    private final NoticeRepository noticeRepository;

    @Override
    @Transactional
    public void execute(JobExecutionContext context) {
        log.info("Notice Job Start");
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusDays(3);
        List<Todo> todoList = todoRepository.findByEndDateBetween(startDate, endDate);

        for (Todo todo : todoList) {
            List<MemberCategory> memberList = todo.getSection().getCategory().getMemberCategories();
            int date = todo.getEndDate().minusYears(startDate.getYear())
                    .minusMonths(startDate.getMonth().getValue())
                    .minusDays(startDate.getDayOfMonth())
                    .getDayOfMonth();

            for (MemberCategory memberCategory : memberList) {
                Long memberId = memberCategory.getMember().getId();

                Notice notice = Notice.builder()
                        .member(memberCategory.getMember())
                        .categoryId(memberCategory.getCategory().getId())
                        .fromName(NoticeType.ENDTODO.getFromName())
                        .text(NoticeType.ENDTODO.getText(memberCategory.getCategory().getName()))
                        .noticeType(NoticeType.ENDTODO)
                        .registTime(LocalDateTime.now())
                        .build();

                noticeRepository.save(notice);
            }
        }

    }
}
