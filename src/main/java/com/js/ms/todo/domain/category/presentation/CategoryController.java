package com.js.ms.todo.domain.category.presentation;

import com.js.ms.todo.domain.category.application.CategoryService;
import com.js.ms.todo.domain.category.presentation.dto.CategorySaveForm;
import com.js.ms.todo.domain.category.presentation.dto.CategoryUpdateForm;
import com.js.ms.todo.global.config.Response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/category", produces = "application/json;charset=UTF-8")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/find")
    public Response findCategoryAll(@AuthenticationPrincipal Long memberId) {
        return categoryService.findCategoryAll(memberId);
    }

    @PostMapping("/save")
    public Response save(@AuthenticationPrincipal Long memberId, @Valid @RequestBody CategorySaveForm dto) {
        return categoryService.save(memberId, dto);
    }

    @PostMapping("/update")
    public Response update(@AuthenticationPrincipal Long memberId, @Valid @RequestBody CategoryUpdateForm dto) {
        return categoryService.update(memberId, dto);
    }

    @PostMapping("/delete/{categoryId}")
    public Response delete(@AuthenticationPrincipal Long memberId, @PathVariable Long categoryId) {
        return categoryService.delete(memberId, categoryId);
    }

    @PostMapping("/share/{categoryId}")
    public Response share(@AuthenticationPrincipal Long memberId, @PathVariable Long categoryId) {
        return categoryService.share(memberId, categoryId);
    }

    @GetMapping("/findShare")
    public Response findShareAll(@AuthenticationPrincipal Long memberId) {
        return categoryService.findShareAll(memberId);
    }


}
