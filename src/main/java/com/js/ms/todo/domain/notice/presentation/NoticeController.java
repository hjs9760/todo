package com.js.ms.todo.domain.notice.presentation;

import com.js.ms.todo.domain.notice.application.NoticeService;
import com.js.ms.todo.domain.notice.presentation.dto.NoticeSaveForm;
import com.js.ms.todo.domain.notice.presentation.dto.NoticeUpdateForm;
import com.js.ms.todo.global.config.Response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping("/save")
    public Response save(@AuthenticationPrincipal Long memberId, @Valid @RequestBody NoticeSaveForm dto) {
        return noticeService.save(memberId, dto);
    }

    @PostMapping("/delete/{noticeId}")
    public Response delete(@PathVariable Long noticeId) {
        return noticeService.delete(noticeId);
    }

    @GetMapping("/find")
    public Response findNotice(@AuthenticationPrincipal Long memberId) {
        return noticeService.findNotice(memberId);
    }

    @PostMapping("/update")
    public Response checkNotice(@AuthenticationPrincipal Long memberId, @Valid @RequestBody NoticeUpdateForm noticeUpdateForm) {
        return noticeService.update(noticeUpdateForm);
    }

}
