package com.js.ms.todo.domain.category.presentation;

import com.js.ms.todo.domain.category.application.CategoryService;
import com.js.ms.todo.domain.category.presentation.dto.CategoryInfo;
import com.js.ms.todo.domain.category.presentation.dto.CategorySaveForm;
import com.js.ms.todo.domain.category.presentation.dto.CategoryUpdateForm;
import com.js.ms.todo.global.config.Response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     *
     * 기능
     * 1. 카테고리 생성, 수정, 삭제, 조회(crud)
     * 2. 카테고리 다른 멤버 초대, 수정, 삭제(crud)
     *
     */

    @GetMapping("/find")
    public List<CategoryInfo> findCategory(@AuthenticationPrincipal Long memberId) {
        return categoryService.findCategory(memberId);
    }

    @PostMapping("/save")
    public Response save(@AuthenticationPrincipal Long memberId, @Valid  @RequestBody CategorySaveForm dto) {
        return categoryService.save(memberId, dto);
    }

    @PostMapping("/update")
    public Response update(@AuthenticationPrincipal Long memberId, @Valid  @RequestBody CategoryUpdateForm dto) {
        return categoryService.update(memberId, dto);
    }

    @PostMapping("/delete/{categoryId}")
    public Response delete(@AuthenticationPrincipal Long memberId, @PathVariable Long categoryId) {
        return categoryService.delete(memberId, categoryId);
    }

//    @PostMapping("/invite")
//    public Response invite(@AuthenticationPrincipal Long memberId, @RequestBody CategoryInviteForm dto) {
//        return categoryService.invite(memberId, dto);
//    }




}
