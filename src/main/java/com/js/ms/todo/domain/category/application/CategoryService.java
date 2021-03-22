package com.js.ms.todo.domain.category.application;

import com.js.ms.todo.domain.category.domain.Category;
import com.js.ms.todo.domain.category.domain.CategoryRepository;
import com.js.ms.todo.domain.category.domain.MemberCategoryRepository;
import com.js.ms.todo.domain.category.presentation.dto.CategoryInfo;
import com.js.ms.todo.domain.category.presentation.dto.CategorySaveForm;
import com.js.ms.todo.domain.category.presentation.dto.CategoryUpdateForm;
import com.js.ms.todo.domain.member.domain.Member;
import com.js.ms.todo.domain.member.domain.MemberCategory;
import com.js.ms.todo.domain.member.domain.MemberRepository;
import com.js.ms.todo.global.config.Response.Response;
import com.js.ms.todo.global.config.exception.ErrorCode;
import com.js.ms.todo.global.config.exception.business.UnAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final MemberCategoryRepository memberCategoryRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Response findCategory(Long memberId) {
        List<MemberCategory> memberCategories = memberCategoryRepository.findByMemberId(memberId);
        List<CategoryInfo> categoryInfos = new ArrayList<>();

        for (MemberCategory memberCategory : memberCategories) {
            Category category = memberCategory.getCategory();
            categoryInfos.add(CategoryInfo.convertFrom(category));
        }

        return Response.of("200", categoryInfos);
    }

    @Transactional
    public Response save(Long memberId, CategorySaveForm dto) {
        Category category = Category.builder()
                .name(dto.getName())
                .status(dto.getStatus())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new UnAuthenticationException());
        category.addMemberCategoryInfo(member);

        if (!ObjectUtils.isEmpty(categoryRepository.save(category))) {
            return Response.of("200", "카테고리가 생성 되었습니다.");
        } else {
            return Response.of(ErrorCode.CATEGORY_SAVE_FAIL);
        }
    }

    @Transactional
    public Response update(Long memberId, CategoryUpdateForm dto) {
        MemberCategory memberCategory = memberCategoryRepository.findByMemberIdAndCategoryId(memberId, dto.getCategoryId());

        if (!ObjectUtils.isEmpty(memberCategory)) {
            Category category = memberCategory.getCategory();
            category.update(dto);
            categoryRepository.save(category);
            return Response.of("200", "카테고리가 수정 되었습니다.");
        }

        return Response.of(ErrorCode.CATEGORY_SAVE_FAIL);
    }

    @Transactional
    public Response delete(Long memberId, Long categoryId) {
        MemberCategory memberCategory = memberCategoryRepository.findByMemberIdAndCategoryId(memberId, categoryId);

        if (!ObjectUtils.isEmpty(memberCategory)) {
            Category category = memberCategory.getCategory();
            categoryRepository.delete(category);
            return Response.of("200", "성공적으로 카테고리가 삭제 되었습니다.");
        }

        return Response.of(ErrorCode.CATEGORY_SAVE_FAIL);
    }
}
