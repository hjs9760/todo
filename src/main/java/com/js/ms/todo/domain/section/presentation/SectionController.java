package com.js.ms.todo.domain.section.presentation;

import com.js.ms.todo.domain.section.application.SectionService;
import com.js.ms.todo.domain.section.presentation.dto.SectionSaveForm;
import com.js.ms.todo.domain.section.presentation.dto.SectionUpdateForm;
import com.js.ms.todo.global.config.Response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/section", produces = "application/json;charset=UTF-8")
public class SectionController {

    private final SectionService sectionService;

    @GetMapping("/find/{categoryId}")
    public Response findSectionByCategory(@PathVariable Long categoryId) {
        return sectionService.findSectionByCategory(categoryId);
    }

    @PostMapping("/save")
    public Response save(@AuthenticationPrincipal Long memberId, @Valid @RequestBody SectionSaveForm dto) {
        return sectionService.save(memberId, dto);
    }

    @PostMapping("/update")
    public Response update(@Valid @RequestBody SectionUpdateForm dto) {
        return sectionService.update(dto);
    }

    @PostMapping("/delete/{sectionId}")
    public Response delete(@PathVariable Long sectionId) {
        return sectionService.delete(sectionId);
    }

}
